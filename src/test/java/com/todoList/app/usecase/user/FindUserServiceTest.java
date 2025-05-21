package com.todoList.app.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.service.user.FindUserService;
import com.todoList.app.domain.model.User;

public class FindUserServiceTest {
    @Test
    void shouldFindUserSuccessfully() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        FindUserService findUserService = new FindUserService(userRepository);

        when(userRepository.findUser(any(int.class)))
        .thenReturn(new User(1, "appleSeed@apple.com", "apple", "123456"));

        // Act
        User user = findUserService.findUser(1);

        // Assert
        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "appleSeed@apple.com");
        assertEquals(user.getPassword(), "123456");
    }

    @Test
    void shouldFindUserEmptyResult() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        FindUserService findUserService = new FindUserService(userRepository);

        when(userRepository.findUser(any(int.class)))
        .thenReturn(null);

        // Act
        User user = findUserService.findUser(1);

        // Assert
        assertNull(user);
    }
}
