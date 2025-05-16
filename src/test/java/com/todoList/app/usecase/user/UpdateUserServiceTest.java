package com.todoList.app.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import com.todoList.app.application.service.user.UpdateUserService;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;
import com.todoList.app.application.port.out.UserRepository;

public class UpdateUserServiceTest {
    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        UpdateUserService updateUserService = new UpdateUserService(userRepository, messageSource);

        when(userRepository.updateUser(any(User.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User user = updateUserService.updateUser(new User(1, "appleseed@apple.com", "123456"));

        // Assert
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

        // Act & Assert
        InvalidUserException exception = assertThrows(
            InvalidUserException.class, 
            () -> updateUserService.updateUser(new User(-1, "applessed@apple.com", "123456")));
        
        assertEquals(exception.getMessage(), "Invalid user id");
        verify(userRepository, never()).updateUser(any(User.class));
    }

    @Test
    void shouldUpdateUserInvalidEmail() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource  messageSource = mock(MessageSource.class);
        UpdateUserService updateUserService = new UpdateUserService(userRepository, messageSource);

        when(messageSource.getMessage(eq("error.user.invalid_email"), any(), any()))
        .thenReturn("Invalid email");

        // Act & Assert
        InvalidUserException exception = assertThrows(
            InvalidUserException.class, 
            () -> updateUserService.updateUser(new User(1, "", "123456")));
        
        assertEquals(exception.getMessage(), "Invalid email");
        verify(userRepository, never()).updateUser(any(User.class));
    }

    @Test
    void shouldUpdateUserInvalidPassword() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource  messageSource = mock(MessageSource.class);
        UpdateUserService updateUserService = new UpdateUserService(userRepository, messageSource);

        when(messageSource.getMessage(eq("error.user.invalid_password"), any(), any()))
        .thenReturn("Invalid password");

        // Act & Assert
        InvalidUserException exception = assertThrows(
            InvalidUserException.class, 
            () -> updateUserService.updateUser(new User(1, "appleseed@apple.com", "")));
        
        assertEquals(exception.getMessage(), "Invalid password");
        verify(userRepository, never()).updateUser(any(User.class));
    }
}
