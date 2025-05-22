package com.todoList.app.application.service.user;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todoList.app.domain.model.User;
import com.todoList.app.adapter.in.controller.dto.CreateUserRequest;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.port.out.helper.MessageHelper;
import com.todoList.app.domain.exception.InvalidUserException;

@Service
public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final MessageHelper messageHelper;
    private final PasswordEncoder encoder;

    public CreateUserService(UserRepository userRepository, MessageHelper messageHelper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.messageHelper = messageHelper;
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
        boolean roleIsEmpty = ObjectUtils.isEmpty(user.getRole().trim());

        if (emailIsEmpty) {
            String message = messageHelper.get("error.user.invalid_email");
            throw new InvalidUserException(message);
        }

        if (passwordIsEmpty) {
            String message = messageHelper.get("error.user.invalid_password");
            throw new InvalidUserException(message);
        }

        if (nameIsEmpty) {
            String message = messageHelper.get("error.user.invalid_name");
            throw new InvalidUserException(message);
        }

        if (roleIsEmpty) {
            String message = messageHelper.get("error.user.invalid_role");
            throw new InvalidUserException(message);
        }
    }

    private void checkUserAlreadyExist(String email) throws InvalidUserException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String message = messageHelper.get("error.user.already_exist");
            throw new InvalidUserException(message);
        }
    }
}
