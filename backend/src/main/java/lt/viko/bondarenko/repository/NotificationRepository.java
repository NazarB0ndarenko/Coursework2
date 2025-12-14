package lt.viko.bondarenko.repository;

import lt.viko.bondarenko.entity.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Long countAllByUserId(Long userId);

    Long countAllByUserIdAndHasBeenRead(Long userId, Boolean hasBeenRead);

    Boolean existsByUserIdAndHasBeenRead(Long userId, Boolean hasBeenRead);

    Page<Notification> findAllByUserId(Long userId, Pageable pageable);

    Page<Notification> findAllByUserIdAndHasBeenRead(Long userId, Boolean hasBeenRead, Pageable pageable);
}