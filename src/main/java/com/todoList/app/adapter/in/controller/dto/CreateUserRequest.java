package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    public CreateUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}