package com.todoList.app.application.port.in.user;

import org.apache.catalina.User;

public interface FindUserUseCase {
    User findUser(int userId);
}
