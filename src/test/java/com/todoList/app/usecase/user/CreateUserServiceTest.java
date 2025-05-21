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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.todoList.app.adapter.in.controller.dto.CreateUserRequest;
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
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

        User expectedUser = new User(0, "appleseed@apple.com", "apple", "123456");

        when(userRepository.createUser(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateUserService createUserService = new CreateUserService(userRepository, messageSource, encoder);
        CreateUserRequest request = new CreateUserRequest("appleseed@apple.com", "apple", "123456");

        // Act
        User createdUser = createUserService.createUser(request);

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
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

        when(messageSource.getMessage(eq("error.user.invalid_email"), any(), any()))
                .thenReturn("Empty email");
        
        CreateUserService createUserService = new CreateUserService(userRepository, messageSource, encoder);
        CreateUserRequest request = new CreateUserRequest("", "apple", "123456");

        // Act & Assert
        InvalidUserException invalidUserException = assertThrows(
            InvalidUserException.class, () -> createUserService.createUser(request)
        );

        assertEquals(invalidUserException.getMessage(), "Empty email");
        verify(userRepository, never()).createUser(any(User.class));
    }

    @Test
    void shouldCreateUserEmptyPasswordField() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        MessageSource messageSource = mock(MessageSource.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

        when(messageSource.getMessage(eq("error.user.invalid_password"), any(), any()))
                .thenReturn("Empty password");
        
        CreateUserService createUserService = new CreateUserService(userRepository, messageSource, encoder);
        CreateUserRequest request = new CreateUserRequest("appleseed@apple.com", "apple", "");

        // Act & Assert
        InvalidUserException invalidUserException = assertThrows(
            InvalidUserException.class, () -> createUserService.createUser(request)
        );

        assertEquals(invalidUserException.getMessage(), "Empty password");
        verify(userRepository, never()).createUser(any(User.class));
    }
}
