package com.todoList.app.application.service.user;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todoList.app.application.port.in.user.LoginUseCase;
import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.application.port.out.helper.MessageHelper;
import com.todoList.app.application.port.out.security.JwtTokenProvider;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;

@Service
public class LoginService implements LoginUseCase {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final MessageHelper messageHelper;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginService(
        UserRepository userRepository, 
        BCryptPasswordEncoder encoder, 
        MessageHelper messageHelper,
        JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.messageHelper = messageHelper;
        this.encoder = encoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(String email, String password) throws InvalidUserException {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            String message = messageHelper.get("error.user.login_erro");
            throw new InvalidUserException(message);
        }

        validateUser(user.get(), password);

        return jwtTokenProvider.generateToken(user.get());
    }

    private void validateUser(User user, String password) {
        boolean passwordIsEmpty = ObjectUtils.isEmpty(password);
        boolean passwordMatch = encoder.matches(password, user.getPassword());

        if (passwordIsEmpty || !passwordMatch) {
            String message = messageHelper.get("error.user.login_erro");
            throw new InvalidUserException(message);
        }
    }
}
