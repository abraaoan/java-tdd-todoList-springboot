package com.todoList.app.application.port.in.user;

import com.todoList.app.domain.exception.InvalidUserException;

public interface DeleteUserUseCase {
    void deleteUser(int userId) throws InvalidUserException;
}
