package com.todoList.app.application.service.user;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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
    public User updateUser(User user) throws InvalidUserException {
        validateUser(user);
        return userRepository.updateUser(user);
    }

    private void validateUser(User user) throws InvalidUserException {
        if (user.getId() <= 0) {
            String message = messageSource.getMessage("error.user.invalid_id", null, Locale.getDefault());
            throw new InvalidUserException(message);
        }

        if (user.getEmail().trim().isEmpty()) {
            String message = messageSource.getMessage("error.user.invalid_email", null, Locale.getDefault());
            throw new InvalidUserException(message);
        }

        if (user.getPassword().trim().isEmpty()) {
            String message = messageSource.getMessage("error.user.invalid_password", null, Locale.getDefault());
            throw new InvalidUserException(message);
        }
    }
}
