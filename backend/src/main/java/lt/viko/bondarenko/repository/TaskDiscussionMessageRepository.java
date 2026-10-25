package lt.viko.bondarenko.repository;

import lt.viko.bondarenko.entity.TaskDiscussionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDiscussionMessageRepository extends JpaRepository<TaskDiscussionMessage, Long> {
    List<TaskDiscussionMessage> findByTaskId(Long taskId);
}
