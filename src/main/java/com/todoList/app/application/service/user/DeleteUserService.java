package com.todoList.app.application.service.user;

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.todoList.app.application.port.in.user.DeleteUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.exception.InvalidUserException;

public class DeleteUserService implements DeleteUserUseCase {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public DeleteUserService(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public void deleteUser(int userId) throws InvalidUserException {
        if (userId <= 0) {
            String message = messageSource.getMessage("error.user.invalid_id", null, Locale.getDefault());
            throw new InvalidUserException(message);
        }

        userRepository.deleteUser(userId);
    }
}
