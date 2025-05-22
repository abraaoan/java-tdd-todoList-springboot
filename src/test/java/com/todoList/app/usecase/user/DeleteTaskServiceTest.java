package com.todoList.app.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.port.out.helper.MessageHelper;
import com.todoList.app.application.service.user.DeleteUserService;
import com.todoList.app.domain.exception.InvalidUserException;

public class DeleteTaskServiceTest {
    @Test
    void souldDeleteSuccessfully() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageHelper messageHelper = mock(MessageHelper.class);
        DeleteUserService deleteUserService = new DeleteUserService(userRepository, messageHelper);

        // Act
        deleteUserService.deleteUser(1);

        // Assert
        verify(userRepository).deleteUser(any(int.class));
    }

    @Test
    void souldDeleteInvalidUserId() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageHelper messageHelper = mock(MessageHelper.class);
        DeleteUserService deleteUserService = new DeleteUserService(userRepository, messageHelper);

        when(messageHelper.get(eq("error.user.invalid_id")))
        .thenReturn("Invalid user id");

        // Act & Assert
        InvalidUserException invalidUserException = assertThrows(
            InvalidUserException.class,
            () -> deleteUserService.deleteUser(-11)
        );
        assertEquals(invalidUserException.getMessage(), "Invalid user id");
        verify(userRepository, never()).deleteUser(any(int.class));
    }
}
