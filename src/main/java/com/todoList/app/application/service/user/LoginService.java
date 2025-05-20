package com.todoList.app.application.service.user;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.todoList.app.application.port.in.user.LoginUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;

public class LoginService implements LoginUseCase {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final MessageSource messageSource;

    public LoginService(
        UserRepository userRepository, 
        BCryptPasswordEncoder encoder, 
        MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.encoder = encoder;
    }

    @Override
    public String login(String email, String password) throws InvalidUserException {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            String message = messageSource.getMessage("error.user.login_erro",null, Locale.getDefault());
            throw new InvalidUserException(message);
        }

        validateUser(user.get(), password);

        return "JWT token";
    }

    private void validateUser(User user, String password) {
        boolean passwordIsEmpty = ObjectUtils.isEmpty(password);
        boolean passwordMatch = encoder.matches(password, user.getPassword());

        if (passwordIsEmpty || !passwordMatch) {
            String message = messageSource.getMessage("error.user.login_erro",null, Locale.getDefault());
            throw new InvalidUserException(message);
        }
    }
    
}
