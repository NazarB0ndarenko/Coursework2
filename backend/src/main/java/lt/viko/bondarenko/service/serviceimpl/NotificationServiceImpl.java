package lt.viko.bondarenko.service.serviceimpl;

import lt.viko.bondarenko.dto.response.BasicMessageDto;
import lt.viko.bondarenko.dto.response.HasUnreadDto;
import lt.viko.bondarenko.dto.response.NotificationShortDto;
import lt.viko.bondarenko.entity.Notification;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.exception.EntityNotFoundException;
import lt.viko.bondarenko.exception.ForbiddenException;
import lt.viko.bondarenko.exception.InvalidInputException;
import lt.viko.bondarenko.repository.NotificationRepository;
import lt.viko.bondarenko.service.NotificationService;
import lt.viko.bondarenko.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final UserService userService;

    public Notification createNotification(User user, String title, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setHasBeenRead(false);

//         Send email notification
//         SimpleMailMessage email = new SimpleMailMessage();
//         email.setTo(user.getEmail());
//         email.setSubject("New Notification: " + title);
//         email.setText(message);
//         mailSender.send(email);

        return notificationRepository.save(notification);
    }

    @Override
    public Page<NotificationShortDto> getAllUserNotifications(Integer page, Integer size) {
        Long loggedInUserId = userService.getLoggedInUserId();
        Long totalCount = notificationRepository.countAllByUserId(loggedInUserId);
        if (totalCount == 0)
            return Page.empty();
        Pageable pageable = getNotificationsPageable(page, size);
        return notificationRepository.findAllByUserId(loggedInUserId, pageable).map(this::notificationToDto);
    }

    @Override
    public Page<NotificationShortDto> getUserUnreadNotifications(Integer page, Integer size) {
        Long loggedInUserId = userService.getLoggedInUserId();
        Long totalCount = notificationRepository.countAllByUserIdAndHasBeenRead(loggedInUserId, false);
        if (totalCount == 0)
            return Page.empty();
        Pageable pageable = getNotificationsPageable(page, size);
        return notificationRepository.findAllByUserIdAndHasBeenRead(loggedInUserId, false, pageable)
                .map(this::notificationToDto);
    }

    @Override
    public Page<NotificationShortDto> getUserReadNotifications(Integer page, Integer size) {
        Long loggedInUserId = userService.getLoggedInUserId();
        Long totalCount = notificationRepository.countAllByUserIdAndHasBeenRead(loggedInUserId, true);
        if (totalCount == 0)
            return Page.empty();
        Pageable pageable = getNotificationsPageable(page, size);
        return notificationRepository.findAllByUserIdAndHasBeenRead(loggedInUserId, true, pageable)
                .map(this::notificationToDto);
    }

    @Override
    public NotificationShortDto markAsRead(Long notificationId) {
        Notification notification = validateAccessAndReturnNotification(notificationId);
        notification.setHasBeenRead(true);
        return notificationToDto(notificationRepository.save(notification));
    }

    @Override
    public HasUnreadDto userHasUnreadNotifications() {
        return new HasUnreadDto(
                notificationRepository.existsByUserIdAndHasBeenRead(userService.getLoggedInUserId(), false));
    }

    @Override
    public BasicMessageDto deleteNotification(Long notificationId) {
        notificationRepository.delete(validateAccessAndReturnNotification(notificationId));
        return new BasicMessageDto("Notification with the id of " + notificationId + " has been succefully deleted.");
    }

    private Pageable getNotificationsPageable(Integer page, Integer size) {
        if (page != null && page < 1)
            throw new InvalidInputException("page", page);
        if (size < 1)
            throw new InvalidInputException("size", size);
        Sort sort = Sort.by("createdAt").descending();
        return page == null ? PageRequest.of(0, Integer.MAX_VALUE, sort)
                : PageRequest.of(page - 1, size, sort);
    }

    private Notification validateAccessAndReturnNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        if (userService.getLoggedInUserId() != notification.getUser().getId() && userService.hasRole("ROLE_USER"))
            throw new ForbiddenException("Users can only manage their own notifications");
        return notification;
    }

    private NotificationShortDto notificationToDto(Notification notification) {
        return NotificationShortDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .hasBeenRead(notification.getHasBeenRead()).build();
    }
}