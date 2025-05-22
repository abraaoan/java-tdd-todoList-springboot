package com.todoList.app.usecase.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.application.service.task.DeleteTaskService;
import com.todoList.app.domain.exception.InvalidTaskException;

public class DeleteTaskServiceTest {
    @Test
    void shouldDeleteTaskSuccessfully() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        int taksID = 1;

        // Act
        DeleteTaskService deleteTaskService = new DeleteTaskService(taskRepository, messageSource);
        deleteTaskService.delete(taksID);

        // Assert
        verify(taskRepository).delete(taksID);
    }

    @Test
    void shouldDeleteTaskWrongTaskID() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);

        String message = "Wroong ID";
        when(messageSource.getMessage(eq("error.task.invalid_id"), any(), any()))
                .thenReturn(message);

        DeleteTaskService deleteTaskService = new DeleteTaskService(taskRepository, messageSource);

        // Act & Assert
        InvalidTaskException exception = assertThrows(
                InvalidTaskException.class,
                () -> deleteTaskService.delete(-1));

        assertEquals(message, exception.getMessage());
        verify(taskRepository, never()).delete(anyInt());
    }

    @Test
    void shouldDeleteTaskWrongRepoMessage() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        DeleteTaskService deleteService = new DeleteTaskService(taskRepository, messageSource);

        doThrow(new RuntimeException("Falha no banco de dados."))
                .when(taskRepository).delete(anyInt());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> deleteService.delete(1));

        assertEquals("Falha no banco de dados.", exception.getMessage());
        verify(taskRepository).delete(anyInt());
    }
}
