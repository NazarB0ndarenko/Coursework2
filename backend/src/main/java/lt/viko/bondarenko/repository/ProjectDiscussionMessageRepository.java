package lt.viko.bondarenko.repository;

import lt.viko.bondarenko.entity.ProjectDiscussionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDiscussionMessageRepository extends JpaRepository<ProjectDiscussionMessage, Integer> {
    List<ProjectDiscussionMessage> findByDiscussionId(Integer discussionId);
}
