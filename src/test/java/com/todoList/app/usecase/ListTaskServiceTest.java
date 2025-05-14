package com.todoList.app.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.application.service.task.ListTaskService;
import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

public class ListTaskServiceTest {
    @Test
    void shouldListTaskSuccessfully() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        int userID = 1;

        ListTaskService listTaskService = new ListTaskService(taskRepository, messageSource);
        when(listTaskService.list(userID)).thenReturn(
            List.of(
                new Task(1, "title #0", false, userID),
                new Task(2, "title #1", false, userID),
                new Task(3, "title #2", false, userID)
            )
        );

        // Act
        List<Task> tasks = listTaskService.list(userID);

        // Assert
        assertEquals(tasks.size(), 3);
        assertEquals(tasks.get(0).getTitle(), "title #0");
        assertEquals(tasks.get(0).getUserID(), userID);
    }

    @Test
    void shouldListTaskWrong() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        ListTaskService listTaskService = new ListTaskService(taskRepository, messageSource);

        when(messageSource.getMessage(eq("error.user.not_found"), any(), any()))
        .thenReturn("Invalid code");

        // Act & Assert
        InvalidTaskException exception = assertThrows(
            InvalidTaskException.class, () -> listTaskService.list(-1)
        );

        assertEquals("Invalid code", exception.getMessage());
        verify(taskRepository, never()).list(anyInt());
    }
}
