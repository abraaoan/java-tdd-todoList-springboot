package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginRequest() {}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
