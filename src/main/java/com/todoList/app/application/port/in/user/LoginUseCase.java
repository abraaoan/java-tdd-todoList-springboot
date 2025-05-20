package com.todoList.app.application.port.in.user;

import com.todoList.app.domain.exception.InvalidUserException;

public interface LoginUseCase {
    String login(String email, String password) throws InvalidUserException;
}
