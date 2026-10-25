package lt.viko.bondarenko.dto.task;

import lt.viko.bondarenko.dto.response.UserShortDto;
import lt.viko.bondarenko.entity.ProjectMember;
import lt.viko.bondarenko.entity.TaskDiscussionMessage;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.exception.EntityNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDiscussionMessageDto {
    private Long id;
    private Long taskId;
    private Long authorId;
    private UserShortDto userInfo;
    private String message;
    private LocalDateTime postedAt;

    public static TaskDiscussionMessageDto fromEntity(TaskDiscussionMessage message) {
        ProjectMember author = message.getAuthor();
        User user = author.getUser();
        if (user == null || author == null)
            throw new EntityNotFoundException("Associated entity is not found!");

        UserShortDto userShortInfo = UserShortDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail()).build();
        return new TaskDiscussionMessageDto(
                message.getId(),
                message.getTask().getId(),
                author.getId(),
                userShortInfo,
                message.getMessage(),
                message.getPostedAt());
    }

    public TaskDiscussionMessage toEntity() {
        TaskDiscussionMessage message = new TaskDiscussionMessage();
        message.setId(this.id);
        message.setMessage(this.message);
        message.setPostedAt(this.postedAt != null ? this.postedAt : LocalDateTime.now());
        return message;
    }
}