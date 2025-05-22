package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class UserResponse {
    private String name;
    @NotBlank private int id;
    @NotBlank private String email;
    @NotBlank private String password;
    @NotBlank private String role;

    public UserResponse(int id, String email, String name, String password, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
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
