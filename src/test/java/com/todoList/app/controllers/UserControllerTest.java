package com.todoList.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.todoList.app.adapter.in.controller.dto.CreateUserRequest;
import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.in.user.DeleteUserUseCase;
import com.todoList.app.application.port.in.user.FindUserUseCase;
import com.todoList.app.application.port.in.user.ListUserUseCase;
import com.todoList.app.application.port.in.user.UpdateUserUseCase;
import com.todoList.app.application.port.out.security.JwtTokenProvider;
import com.todoList.app.domain.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtService;

    @MockBean
    private CreateUserUseCase createUserUseCase;
    @MockBean
    private FindUserUseCase findUserUseCase;
    @MockBean
    private ListUserUseCase listUserUseCase;
    @MockBean
    private UpdateUserUseCase updateUserUsecase;
    @MockBean
    private DeleteUserUseCase deleteUserUsecase;

    @Test
    void shouldCreateUserAndReturn201() throws Exception {
        // Arrange
        User newUser = new User(1, "appleseed@apple.com", "apple", "123456", "user");
        when(createUserUseCase.createUser(any(CreateUserRequest.class)))
                .thenReturn(newUser);

        String payload = "{\"email\": \"appleseed@apple.com\", \"name\": \"apple\", \"password\": \"123456\", \"role\": \"user\"}";
        String token = jwtService.generateToken(new User(1, "appleseed@apple.com", "apple", "123456", "user"));

        // Act & Assert
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("appleseed@apple.com"));
    }

    @Test
    void shouldFindUserAndReturn200() throws Exception {
        // Arrange
        User newUser = new User(1, "appleseed@apple.com", "apple", "123456", "user");
        when(findUserUseCase.findUser(any(int.class)))
                .thenReturn(newUser);
        String token = jwtService.generateToken(new User(1, "appleseed@apple.com", "apple", "123456", "user"));

        // Act & Assert
        mockMvc.perform(get("/user?userId=1").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("appleseed@apple.com"));
    }

    @Test
    void shouldListUserAndReturn200() throws Exception {
        // Arrange
        List<User> users = List.of(
                new User(1, "appleseed@apple.com", "Pedro", "123456", "user"),
                new User(2, "pineappleseed@pineapple.com", "Maria", "123456", "user"));
        when(listUserUseCase.listUsers()).thenReturn(users);
        String token = jwtService.generateToken(new User(1, "appleseed@apple.com", "apple", "123456", "user"));

        // Act & Assert
        mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("appleseed@apple.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("pineappleseed@pineapple.com"));
    }

    @Test
    void shouldUpdateUserAndReturn200() throws Exception {
        // Arrange
        User newUser = new User(1, "appleseed@apple.com", "apple", "123456", "user");
        when(updateUserUsecase.updateUser(any(UpdateUserRequest.class)))
                .thenReturn(newUser);

        String payload = "{\"id\": 1, \"name\": \"apple\"}";
        String token = jwtService.generateToken(new User(1, "appleseed@apple.com", "apple", "123456", "user"));

        // Act & Assert
        mockMvc.perform(patch("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("apple"))
                .andExpect(jsonPath("$.email").value("appleseed@apple.com"));
    }

    @Test
    void shouldDeleteUserAndReturn200() throws Exception {
        // Arrange
        String payload = "{\"userId\": 1}";
        String token = jwtService.generateToken(new User(1, "appleseed@apple.com", "apple", "123456", "user"));

        // Act & Assert
        mockMvc.perform(delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(payload))
                .andExpect(status().isNoContent());
    }
}
