package lt.viko.bondarenko.service.serviceimpl;

import lt.viko.bondarenko.entity.ProjectDiscussion;
import lt.viko.bondarenko.entity.ProjectDiscussionMessage;
import lt.viko.bondarenko.entity.ProjectMember;
import lt.viko.bondarenko.exception.EntityNotFoundException;
import lt.viko.bondarenko.repository.ProjectDiscussionRepository;
import lt.viko.bondarenko.repository.ProjectDiscussionMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DiscussionServiceImpl {

    private final ProjectDiscussionRepository projectDiscussionRepository;
    private final ProjectDiscussionMessageRepository projectDiscussionMessageRepository;

    public DiscussionServiceImpl(ProjectDiscussionRepository projectDiscussionRepository,
                                 ProjectDiscussionMessageRepository projectDiscussionMessageRepository) {
        this.projectDiscussionRepository = projectDiscussionRepository;
        this.projectDiscussionMessageRepository = projectDiscussionMessageRepository;
    }

    public ProjectDiscussion createDiscussion(String title, ProjectMember postedBy, ProjectDiscussion project) {
        ProjectDiscussion discussion = new ProjectDiscussion();
        discussion.setTitle(title);
        discussion.setPostedBy(postedBy);
        discussion.setProject(project.getProject());
        discussion.setCreatedAt(LocalDateTime.now());
        return projectDiscussionRepository.save(discussion);
    }

    public List<ProjectDiscussionMessage> getMessagesForDiscussion(Long discussionId) {
        return projectDiscussionMessageRepository.findByDiscussionId(discussionId.intValue());
    }

    public ProjectDiscussionMessage addMessageToDiscussion(Long discussionId, String message, ProjectMember postedBy) {
        ProjectDiscussion discussion = projectDiscussionRepository.findById(discussionId.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found with ID: " + discussionId));

        ProjectDiscussionMessage discussionMessage = new ProjectDiscussionMessage();
        discussionMessage.setMessage(message);
        discussionMessage.setPostedBy(postedBy);
        discussionMessage.setDiscussion(discussion);
        discussionMessage.setCreatedAt(LocalDateTime.now());

        return projectDiscussionMessageRepository.save(discussionMessage);
    }

    public void deleteMessage(Long messageId) {
        ProjectDiscussionMessage message = projectDiscussionMessageRepository.findById(messageId.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + messageId));

        projectDiscussionMessageRepository.delete(message);
    }
}