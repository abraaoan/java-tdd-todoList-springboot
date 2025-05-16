package com.todoList.app.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.service.user.CreateUserService;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;

public class CreateUserServiceTest {
    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource messageSource = mock(MessageSource.class);

        User expectedUser = new User(0, "appleseed@apple.com", "123456");

        when(userRepository.createUser(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateUserService createUserService = new CreateUserService(userRepository, messageSource);

        // Act
        User createdUser = createUserService.createUser(
                "appleseed@apple.com",
                "123456");

        // Assert
        assertNotNull(createdUser);
        assertEquals(expectedUser.getEmail(), createdUser.getEmail());
        verify(userRepository).createUser(any(User.class));
    }

    @Test
    void shouldCreateUserEmptyEmailField() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource messageSource = mock(MessageSource.class);

        when(messageSource.getMessage(eq("error.task.invalid_email"), any(), any()))
                .thenReturn("Empty email");
        
        CreateUserService createUserService = new CreateUserService(userRepository, messageSource);

        // Act & Assert
        InvalidUserException invalidUserException = assertThrows(
            InvalidUserException.class, () -> createUserService.createUser("", "123456")
        );

        assertEquals(invalidUserException.getMessage(), "Empty email");
        verify(userRepository, never()).createUser(any(User.class));
    }

    @Test
    void shouldCreateUserEmptyPasswordField() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource messageSource = mock(MessageSource.class);

        when(messageSource.getMessage(eq("error.task.invalid_password"), any(), any()))
                .thenReturn("Empty password");
        
        CreateUserService createUserService = new CreateUserService(userRepository, messageSource);

        // Act & Assert
        InvalidUserException invalidUserException = assertThrows(
            InvalidUserException.class, () -> createUserService.createUser("a@a.com", "")
        );

        assertEquals(invalidUserException.getMessage(), "Empty password");
        verify(userRepository, never()).createUser(any(User.class));
    }
}
