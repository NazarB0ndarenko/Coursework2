package lt.viko.bondarenko.service;

import lt.viko.bondarenko.dto.task.TaskDiscussionMessageDto;
import lt.viko.bondarenko.dto.task.TaskDto;
import lt.viko.bondarenko.dto.task.TaskLabelDto;
import lt.viko.bondarenko.dto.task.TaskWithAssigneesDto;
import lt.viko.bondarenko.dto.task.TaskWithLabelsDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskDto updateTask(Long taskId, TaskDto taskDto);

    TaskDto updateTaskStatus(Long taskId, String status);

    List<TaskDto> getTasksByProject(Long projectId);

    List<TaskDto> getTasksByProjectAndStatus(Long projectId, String status);

    boolean deleteTask(Long taskId);

    List<TaskLabelDto> getLabelsForTask(Long taskId);

    TaskLabelDto createLabel(TaskLabelDto taskLabelDto);

    TaskLabelDto updateLabel(Long labelId, TaskLabelDto taskLabelDto);

    boolean deleteFreeLabel(Long labelId);

    TaskWithLabelsDto addLabelToTask(Long taskId, Long labelId);

    TaskWithLabelsDto removeLabelFromTask(Long taskId, Long labelId);

    TaskWithLabelsDto getTaskWithLabels(Long taskId);

    TaskDiscussionMessageDto addMessageToTask(Long taskId, TaskDiscussionMessageDto messageDto);

    boolean deleteMessage(Long messageId);

    List<TaskDiscussionMessageDto> getAllMessagesForTask(Long taskId);

    TaskWithAssigneesDto assignMemberToTask(Long taskId, Long memberId);

    void unassignMemberFromTask(Long taskId, Long memberId);

    TaskWithAssigneesDto getAllAssigneesForTask(Long taskId);
}
