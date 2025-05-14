package com.todoList.app.application.service.user;


import com.todoList.app.domain.model.User;

import org.springframework.stereotype.Service;

import com.todoList.app.application.port.in.user.FindUserUseCase;
import com.todoList.app.application.port.out.UserRepository;

@Service
public class FindUserService implements FindUserUseCase {
    private final UserRepository userRepository;

    public FindUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(int userId) {
        return userRepository.findUser(userId);
    }
}