package com.todoList.app.application.port.in.user;

import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;

public interface UpdateUserUseCase {
    User updateUser(User user) throws InvalidUserException;
}
