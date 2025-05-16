package com.todoList.app.usecase.task;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Locale;

import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.application.service.task.CreateTaskService;

public class CreateTaskServiceTest {
    @Test
    void shouldCreateTaskWithValidTitle() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = new ReloadableResourceBundleMessageSource() {
            {
                setBasename("classpath:messages");
                setDefaultEncoding("UTF-8");
            }
        };
        CreateTaskService createTaskService = new CreateTaskService(taskRepository, messageSource);

        // Simula o comportamento do repositório (retorna a própria task)
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Task createdTask = createTaskService.create("Mock Title #0", 1);

        // Assert
        assertNotNull(createdTask);
        assertEquals("Mock Title #0", createdTask.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);

        String expectedMessage = "Título da tarefa é obrigatório.";

        when(messageSource.getMessage(eq("error.task.invalid_title"), any(), any(Locale.class)))
                .thenReturn(expectedMessage);

        CreateTaskService createTaskService = new CreateTaskService(taskRepository, messageSource);

        // Act & Assert
        InvalidTaskException exception = assertThrows(
            InvalidTaskException.class,
            () -> createTaskService.create("   ", 1)
        );

        assertEquals(expectedMessage, exception.getMessage());
        verify(messageSource).getMessage(eq("error.task.invalid_title"), any(), any(Locale.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIDIsMinorOrEqualtoZero() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);

        String expectedMessage = "Usuário não encontrado.";

        when(messageSource.getMessage(eq("error.user.not_found"), any(), any(Locale.class)))
                .thenReturn(expectedMessage);

        CreateTaskService createTaskService = new CreateTaskService(taskRepository, messageSource);

        // Act & Assert
        InvalidTaskException exception = assertThrows(
            InvalidTaskException.class,
            () -> createTaskService.create("Title #1", -1)
        );

        assertEquals(expectedMessage, exception.getMessage());
        verify(messageSource).getMessage(eq("error.user.not_found"), any(), any(Locale.class));
    }
}
