package com.todoList.app.application.service.user;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.application.port.in.user.UpdateUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;

@Service
public class UpdateUserService implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UpdateUserService(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public User updateUser(UpdateUserRequest request) throws InvalidUserException {
        validateUser(request);
        User user = userRepository.findUser(request.getId());
        user.setName(request.getName());
        return userRepository.updateUser(user);
    }

    private void validateUser(UpdateUserRequest user) throws InvalidUserException {
        boolean nameIsEmpty = ObjectUtils.isEmpty(user.getName().trim());

        if (user.getId() <= 0) {
            String message = messageSource.getMessage("error.user.invalid_id", null, Locale.getDefault());
            throw new InvalidUserException(message);
        }

        if (nameIsEmpty) {
            String message = messageSource.getMessage("error.user.invalid_name", null, Locale.getDefault());
            throw new InvalidUserException(message);
        }
    }
}
