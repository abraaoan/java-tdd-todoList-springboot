package com.todoList.app.adapter.in.controller.dto;

public class UpdateUserRequest {
    private int id;
    private String email;
    private String password;
    
    public UpdateUserRequest() {}
    
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
