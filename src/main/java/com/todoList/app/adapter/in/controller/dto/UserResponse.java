package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class UserResponse {
    @NotBlank
    private int id;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public UserResponse(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
