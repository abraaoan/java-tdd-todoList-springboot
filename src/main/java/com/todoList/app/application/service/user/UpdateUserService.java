package com.todoList.app.application.service.user;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.application.port.in.user.UpdateUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.port.out.helper.MessageHelper;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;

@Service
public class UpdateUserService implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final MessageHelper messageHelper;

    public UpdateUserService(UserRepository userRepository, MessageHelper messageHelper) {
        this.userRepository = userRepository;
        this.messageHelper = messageHelper;
    }

    @Override
    public User updateUser(UpdateUserRequest request) throws InvalidUserException {
        validateUser(request);
        User user = userRepository.findUser(request.getId());
        user.setName(request.getName());
        user.setRole(request.getRole());
        return userRepository.updateUser(user);
    }

    private void validateUser(UpdateUserRequest user) throws InvalidUserException {
        boolean nameIsEmpty = ObjectUtils.isEmpty(user.getName().trim());

        if (user.getId() <= 0) {
            String message = messageHelper.get("error.user.invalid_id");
            throw new InvalidUserException(message);
        }

        if (nameIsEmpty) {
            String message = messageHelper.get("error.user.invalid_name");
            throw new InvalidUserException(message);
        }
    }
}
