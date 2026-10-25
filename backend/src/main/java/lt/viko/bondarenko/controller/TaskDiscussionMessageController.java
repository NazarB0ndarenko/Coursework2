package lt.viko.bondarenko.controller;

import lt.viko.bondarenko.entity.TaskDiscussionMessage;
import lt.viko.bondarenko.service.serviceimpl.TaskDiscussionMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
public class TaskDiscussionMessageController {

    @Autowired
    private TaskDiscussionMessageServiceImpl taskDiscussionMessageService;

    // Endpoint to add a new comment on a task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDiscussionMessage addComment(@PathVariable Long taskId, @RequestBody String message) {
        return taskDiscussionMessageService.addComment(taskId, message);
    }

    // Endpoint to fetch all comments for a specific task
    @GetMapping
    public Iterable<TaskDiscussionMessage> getComments(@PathVariable Long taskId) {
        return taskDiscussionMessageService.getComments(taskId);
    }
}
