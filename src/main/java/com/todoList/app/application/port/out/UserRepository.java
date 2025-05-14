package com.todoList.app.application.port.out;

import java.util.List;

import com.todoList.app.domain.model.User;
import com.todoList.app.domain.exception.InvalidUserException;

public interface UserRepository {
    User createUser(String email, String password) throws InvalidUserException;
    void deleteUser(int userId) throws InvalidUserException;
    User findUser(int userId);
    List<User> listUser();
    User updateUser(User user);
}
