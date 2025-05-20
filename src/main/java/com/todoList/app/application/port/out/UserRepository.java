package com.todoList.app.application.port.out;

import java.util.List;
import java.util.Optional;

import com.todoList.app.domain.model.User;
import com.todoList.app.domain.exception.InvalidUserException;

public interface UserRepository {
    User createUser(User user) throws InvalidUserException;
    void deleteUser(int userId) throws InvalidUserException;
    User findUser(int userId);
    Optional<User> findByEmail(String email);
    List<User> listUser();
    User updateUser(User user);
    String login(String email, String password);
}
