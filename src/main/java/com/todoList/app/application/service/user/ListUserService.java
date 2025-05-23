package com.todoList.app.application.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todoList.app.domain.model.User;
import com.todoList.app.application.port.in.user.ListUserUseCase;
import com.todoList.app.application.port.out.UserRepository;

@Service
public class ListUserService implements ListUserUseCase {
    private final UserRepository userRepository;

    public ListUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.listUser();
    }
}
