package com.todoList.app.application.port.out.security;

import com.todoList.app.domain.model.User;

public interface JwtTokenProvider {
    String generateToken(User user);
    boolean isValid(String token);
    String extractSubject(String token);
    String extractRole(String token);
}
