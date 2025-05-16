package com.todoList.app.application.service.user;

import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.todoList.app.domain.model.User;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.exception.InvalidUserException;

@Service
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
        checkUserAlreadyExist(email);

        User user = new User(0, email, password);
        return userRepository.createUser(user);
    }

    private void validFields(String email, String password) throws InvalidUserException {
        if (email.trim().isEmpty()) {
            String msg = messageSource.getMessage("error.user.invalid_email", null, Locale.getDefault());
            throw new InvalidUserException(msg);
        }

        if (password.trim().isEmpty()) {
            String msg = messageSource.getMessage("error.user.invalid_password", null, Locale.getDefault());
            throw new InvalidUserException(msg);
        }
    }

    private void checkUserAlreadyExist(String email) throws InvalidUserException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String message = messageSource.getMessage("error.user.already_exist", null, Locale.getDefault());
            throw new InvalidUserException(message);
        }
    }
}
