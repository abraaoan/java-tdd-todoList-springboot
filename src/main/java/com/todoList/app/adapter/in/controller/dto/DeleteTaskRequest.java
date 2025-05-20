package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class DeleteTaskRequest {
    @NotBlank
    private int taskId;

    public DeleteTaskRequest() {}

    public int getTaskId() {
        return taskId;
    }
}
