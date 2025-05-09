package com.todoList.app.domain.model;

public class Task {
    private int id;
    private String title;
    private Boolean status;
    private int userID;

    public Task(int id, String title, Boolean status, int userID) {
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
