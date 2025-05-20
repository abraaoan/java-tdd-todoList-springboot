package com.todoList.app.application.port.in.user;

import com.todoList.app.domain.model.User;
import com.todoList.app.adapter.in.controller.dto.CreateUserRequest;
import com.todoList.app.domain.exception.InvalidUserException;

public interface CreateUserUseCase {
    User createUser(CreateUserRequest request) throws InvalidUserException;
}
