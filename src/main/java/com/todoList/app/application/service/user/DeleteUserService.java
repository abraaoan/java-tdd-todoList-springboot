package com.todoList.app.application.service.user;

import org.springframework.stereotype.Service;

import com.todoList.app.application.port.in.user.DeleteUserUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.port.out.helper.MessageHelper;
import com.todoList.app.domain.exception.InvalidUserException;

@Service
public class DeleteUserService implements DeleteUserUseCase {
    private final UserRepository userRepository;
    private final MessageHelper messageHelper;

    public DeleteUserService(UserRepository userRepository, MessageHelper messageHelper) {
        this.userRepository = userRepository;
        this.messageHelper = messageHelper;
    }

    @Override
    public void deleteUser(int userId) throws InvalidUserException {
        if (userId <= 0) {
            String message = messageHelper.get("error.user.invalid_id");
            throw new InvalidUserException(message);
        }

        userRepository.deleteUser(userId);
    }
}
