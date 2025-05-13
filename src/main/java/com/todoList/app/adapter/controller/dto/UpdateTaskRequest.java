package com.todoList.app.adapter.controller.dto;

public class UpdateTaskRequest {
    private int id;
    private String title;
    private Boolean completed;
    private int userId;

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
