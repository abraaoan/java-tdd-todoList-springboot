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
import com.todoList.app.domain.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @Test
    void shouldCreateUserAndReturn201() throws Exception {
        // Arrange
        User newUser = new User(1, "appleseed@apple.com", "123456");
        when(createUserUseCase.createUser(any(), any()))
                .thenReturn(newUser);

        String payload = "{\"id\": 1, \"email\": \"appleseed@apple.com\", \"password\": \"123456\"}";

        // Act & Arrange
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.email").value("appleseed@apple.com"));
    }
}
