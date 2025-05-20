package com.todoList.app.adapter.in.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskResponse {
    @NotBlank
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private Boolean status;
    @NotBlank
    private int userID;

    public TaskResponse(int id, String title, Boolean status, int userID) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getStatus() {
        return status;
    }

    public int getUserID() {
        return userID;
    }
}
