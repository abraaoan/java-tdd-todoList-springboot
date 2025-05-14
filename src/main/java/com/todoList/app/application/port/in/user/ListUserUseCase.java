package com.todoList.app.application.port.in.user;

import java.util.List;

import org.apache.catalina.User;

public interface ListUserUseCase {
    List<User> listUsers();
}
