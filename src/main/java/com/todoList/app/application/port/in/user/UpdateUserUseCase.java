package com.todoList.app.application.port.in.user;

import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;

public interface UpdateUserUseCase {
    User updateUser(UpdateUserRequest user) throws InvalidUserException;
}
