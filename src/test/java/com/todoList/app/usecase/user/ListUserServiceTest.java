package com.todoList.app.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.service.user.ListUserService;
import com.todoList.app.domain.model.User;

public class ListUserServiceTest {
    @Test
    void shouldListUserSuccessfully() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        ListUserService listUserService = new ListUserService(userRepository);

        when(userRepository.listUser())
                .thenReturn(List.of(
                        new User(1, "appleseed@apple.com", "apple", "123456", "user"),
                        new User(2, "pineappleseed@pineapple.com", "pineapple", "123456", "user"),
                        new User(3, "orangeseed@orange.com","orange", "123456", "user")));

        // Act
        List<User> users = listUserService.listUsers();

        // Assert
        assertEquals(users.size(), 3);
        assertEquals(users.get(0).getEmail(), "appleseed@apple.com");
        assertEquals(users.get(2).getEmail(), "orangeseed@orange.com");
    }

    @Test
    void shouldListUserEmptyResult() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        ListUserService listUserService = new ListUserService(userRepository);

        when(userRepository.listUser()).thenReturn(List.of());

        // Act
        List<User> users = listUserService.listUsers();

        // Assert
        assertEquals(users.size(), 0);
    }
}
