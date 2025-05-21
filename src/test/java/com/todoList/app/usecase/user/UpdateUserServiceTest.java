package com.todoList.app.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import com.todoList.app.application.service.user.UpdateUserService;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;
import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.application.port.out.UserRepository;

public class UpdateUserServiceTest {
    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        UpdateUserService updateUserService = new UpdateUserService(userRepository, messageSource);
        User expectedUser = new User(1, "appleseed@apple.com", "", "123456");

        when(userRepository.updateUser(any(User.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findUser(any(int.class)))
        .thenReturn(expectedUser);

        UpdateUserRequest request = new UpdateUserRequest(1, "apple");

        // Act
        User user = updateUserService.updateUser(request);

        // Assert
        assertEquals(user.getName(), "apple");
        assertEquals(user.getEmail(), "appleseed@apple.com");
    }

    @Test
    void shouldUpdateUserInvalidId() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource  messageSource = mock(MessageSource.class);
        UpdateUserService updateUserService = new UpdateUserService(userRepository, messageSource);

        when(messageSource.getMessage(eq("error.user.invalid_id"), any(), any()))
        .thenReturn("Invalid user id");
        UpdateUserRequest request = new UpdateUserRequest(-1, "apple");

        // Act & Assert
        InvalidUserException exception = assertThrows(
            InvalidUserException.class, 
            () -> updateUserService.updateUser(request));
        
        assertEquals(exception.getMessage(), "Invalid user id");
        verify(userRepository, never()).updateUser(any(User.class));
    }
}
