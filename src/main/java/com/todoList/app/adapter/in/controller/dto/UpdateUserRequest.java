package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {
    @NotBlank private int id;
    @NotBlank private String name;
    @NotBlank private String role;
    
    public UpdateUserRequest() {}

    public UpdateUserRequest(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
