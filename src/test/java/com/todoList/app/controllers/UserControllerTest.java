package com.todoList.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.todoList.app.adapter.in.controller.UserController;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.in.user.DeleteUserUseCase;
import com.todoList.app.application.port.in.user.FindUserUseCase;
import com.todoList.app.application.port.in.user.ListUserUseCase;
import com.todoList.app.application.port.in.user.UpdateUserUseCase;
import com.todoList.app.domain.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        User newUser = new User(1, "appleseed@apple.com", "123456");
        when(createUserUseCase.createUser(any(), any()))
                .thenReturn(newUser);

        String payload = "{\"id\": 1, \"email\": \"appleseed@apple.com\", \"password\": \"123456\"}";

        // Act & Assert
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("appleseed@apple.com"));
    }

    @Test
    void shouldFindUserAndReturn200() throws Exception {
        // Arrange
        User newUser = new User(1, "appleseed@apple.com", "123456");
        when(findUserUseCase.findUser(any(int.class)))
                .thenReturn(newUser);

        // Act & Assert
        mockMvc.perform(get("/user?userId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("appleseed@apple.com"));
    }

    @Test
    void shouldListUserAndReturn200() throws Exception {
        // Arrange
        List<User> users = List.of(
                new User(1, "appleseed@apple.com", "123456"),
                new User(2, "pineappleseed@pineapple.com", "123456"));
        when(listUserUseCase.listUsers())
                .thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("appleseed@apple.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("pineappleseed@pineapple.com"));
    }

    @Test
    void shouldUpdateUserAndReturn200() throws Exception {
        // Arrange
        User newUser = new User(1, "appleseed@apple.com", "123456");
        when(updateUserUsecase.updateUser(any(User.class)))
                .thenReturn(newUser);
        
        String payload = "{\"id\": 1, \"email\": \"appleseed@apple.com\", \"password\": \"123456\"}";

        // Act & Assert
        mockMvc.perform(patch("/user")
        .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("appleseed@apple.com"));
    }

    @Test
    void shouldDeleteUserAndReturn200() throws Exception {
        // Arrange        
        String payload = "{\"userId\": 1}";

        // Act & Assert
        mockMvc.perform(delete("/user")
        .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isNoContent());
    }
}
