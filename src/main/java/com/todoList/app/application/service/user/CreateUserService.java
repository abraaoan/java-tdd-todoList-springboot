package com.todoList.app.application.service.user;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todoList.app.domain.model.User;
import com.todoList.app.adapter.in.controller.dto.CreateUserRequest;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.exception.InvalidUserException;

@Service
public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder encoder;

    public CreateUserService(UserRepository userRepository, MessageSource messageSource, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.encoder = encoder;
    }

    @Override
    public User createUser(CreateUserRequest request) throws InvalidUserException {
        validFields(request);
        checkUserAlreadyExist(request.getEmail());
        String encryptedPassword = encoder.encode(request.getPassword());
        User user = new User(0, request.getEmail(), request.getName(), encryptedPassword, request.getRole());
        return userRepository.createUser(user);
    }

    private void validFields(CreateUserRequest user) throws InvalidUserException {
        boolean emailIsEmpty = ObjectUtils.isEmpty(user.getEmail().trim());
        boolean passwordIsEmpty = ObjectUtils.isEmpty(user.getPassword().trim());
        boolean nameIsEmpty = ObjectUtils.isEmpty(user.getName().trim());

        if (emailIsEmpty) {
            String msg = messageSource.getMessage("error.user.invalid_email", null, Locale.getDefault());
            throw new InvalidUserException(msg);
        }

        if (passwordIsEmpty) {
            String msg = messageSource.getMessage("error.user.invalid_password", null, Locale.getDefault());
            throw new InvalidUserException(msg);
        }

        if (nameIsEmpty) {
            String msg = messageSource.getMessage("error.user.invalid_name", null, Locale.getDefault());
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
