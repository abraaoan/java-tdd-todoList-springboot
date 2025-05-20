package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserRequest {
    @NotBlank
    private int userId;

    public DeleteUserRequest() {}

    public int getUserId() {
        return userId;
    }
}
