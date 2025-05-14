package com.todoList.app.application.port.in.user;

import com.todoList.app.domain.model.User;

public interface FindUserUseCase {
    User findUser(int userId);
}
