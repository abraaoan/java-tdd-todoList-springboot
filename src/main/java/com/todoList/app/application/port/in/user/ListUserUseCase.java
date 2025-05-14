package com.todoList.app.application.port.in.user;

import java.util.List;

import com.todoList.app.domain.model.User;

public interface ListUserUseCase {
    List<User> listUsers();
}
