package com.todoList.app.adapter.in.controller.dto;

public class CreateTaskRequest {
    private String title;
    private int userId;

    public CreateTaskRequest() {}

    CreateTaskRequest(String title, int userId) {
        this.title = title;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public int getUserId() {
        return userId;
    }
}
