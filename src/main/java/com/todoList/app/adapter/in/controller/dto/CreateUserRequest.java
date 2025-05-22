package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @Email(message = "{error.user.invalid_email}")
    private String email;
    @NotBlank
    private String name;
    @Size(min = 6,  message = "{error.user.too_short}")
    private String password;
    @NotBlank
    private String role;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String email, String name, String password, String Role, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
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

    public String getRole() {
        return role;
    }
}