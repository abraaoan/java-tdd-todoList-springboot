package com.todoList.app.application.port.in.user;

import org.apache.catalina.User;

import com.todoList.app.domain.exception.InvalidUserException;

public interface CreateUserUseCase {
    User createUser(String email, String password) throws InvalidUserException;
}
