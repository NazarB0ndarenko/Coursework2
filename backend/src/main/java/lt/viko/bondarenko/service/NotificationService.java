package lt.viko.bondarenko.service;


import org.springframework.data.domain.Page;

import lt.viko.bondarenko.dto.response.BasicMessageDto;
import lt.viko.bondarenko.dto.response.HasUnreadDto;
import lt.viko.bondarenko.dto.response.NotificationShortDto;
import lt.viko.bondarenko.entity.Notification;
import lt.viko.bondarenko.entity.User;

public interface NotificationService {
    Notification createNotification(User user, String title, String message);

    HasUnreadDto userHasUnreadNotifications();

    Page<NotificationShortDto> getAllUserNotifications(Integer page, Integer size);

    Page<NotificationShortDto> getUserUnreadNotifications(Integer page, Integer size);

    Page<NotificationShortDto> getUserReadNotifications(Integer page, Integer size);

    BasicMessageDto deleteNotification(Long notificationId);

    NotificationShortDto markAsRead(Long notificationId);
}
