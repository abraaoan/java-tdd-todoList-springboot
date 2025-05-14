package com.todoList.app.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.application.service.task.UpdateTaskService;
import com.todoList.app.domain.model.Task;

public class UpdateTaskServiceTest {
    @Test
    void shouldUpdateTaskSuccessfully() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        UpdateTaskService updateTaskService = new UpdateTaskService(taskRepository);
        Task mockTask = new Task(1, "title #01", false, 1);

        // Act
        updateTaskService.update(mockTask);

        // Assert
        verify(taskRepository).update(any(Task.class));
    }
}
