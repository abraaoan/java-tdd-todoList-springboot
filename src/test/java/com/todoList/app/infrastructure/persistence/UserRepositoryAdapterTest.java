package com.todoList.app.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.model.User;

@SpringBootTest
public class UserRepositoryAdapterTest {
    @Autowired
    UserRepository userRepository;
    
    @Test
    void shouldSaveUserAndRetriveIt() {
        // Arrange
        User newUser = new User(1, "appleseed@apple.com", "1233456");

        // Act
        User createdUser = userRepository.createUser(newUser);

        // Assert
        assertNotNull(createdUser);
    }
}
