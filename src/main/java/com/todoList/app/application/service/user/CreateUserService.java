package com.todoList.app.application.service.user;

import java.util.Locale;

import org.apache.catalina.User;
import org.springframework.context.MessageSource;

import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.exception.InvalidUserException;

public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public CreateUserService(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public User createUser(String email, String password) throws InvalidUserException {
        validFields(email, password);
        return userRepository.createUser(email, password);
    }

    private void validFields(String email, String password) throws InvalidUserException {
        if (email.trim().isEmpty()) {
            String msg = messageSource.getMessage("error.task.invalid_email", null, Locale.getDefault());
            throw new InvalidUserException(msg);
        }

        if (password.trim().isEmpty()) {
            String msg = messageSource.getMessage("error.task.invalid_password", null, Locale.getDefault());
            throw new InvalidUserException(msg);
        }
    }
    
}
