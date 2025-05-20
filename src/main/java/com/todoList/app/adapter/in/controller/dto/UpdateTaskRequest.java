package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateTaskRequest {
    @NotBlank
    private int id;
    @NotBlank
    private String title;
    @NotNull
    private Boolean completed;
    @NotBlank
    private int userId;

    public UpdateTaskRequest() {}

    public UpdateTaskRequest(int id, String title, Boolean completed, int userId) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public int getUserId() {
        return userId;
    }
}
