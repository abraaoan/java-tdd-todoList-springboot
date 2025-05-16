package com.todoList.app.usecase.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.application.service.task.FindTaskService;
import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

public class FindTaskServiceTest {
    @Test
    void shouldFindTaskSuccessfully() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        FindTaskService findTaskService = new FindTaskService(taskRepository, messageSource);

        int taskID = 1;

        when(findTaskService.findById(taskID))
        .thenReturn(new Task(1, "Title #1", false, 1));

        // Act
        Task task = findTaskService.findById(taskID);

        // Assert
        assertEquals(task.getTitle(), "Title #1");
        assertEquals(task.getStatus(), false);
        assertEquals(task.getUserID(), 1);
    }

    @Test
    void shouldFindTaskWrongID() {
        // Arrange
        TaskRepository taskRepository = mock(TaskRepository.class);
        MessageSource messageSource = mock(MessageSource.class);

        String expectedMessage = "ID da tarefa inválido";

        when(messageSource.getMessage(eq("error.task.invalid_id"), any(), any()))
        .thenReturn(expectedMessage);
        
        FindTaskService findTaskService = new FindTaskService(taskRepository, messageSource);

        // Act & Arrange
        InvalidTaskException exception = assertThrows(
            InvalidTaskException.class, 
            () -> findTaskService.findById(-1)
        );

        assertEquals(expectedMessage, exception.getMessage());
        verify(taskRepository, never()).findById(anyInt());
    }
}
