package com.todoList.app.adapter.in.controller.mapper;

import com.todoList.app.adapter.in.controller.dto.UserResponse;
import com.todoList.app.domain.model.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword());
    }
}
