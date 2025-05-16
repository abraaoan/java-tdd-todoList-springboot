package com.todoList.app.adapter.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoList.app.application.port.in.user.FindUserUseCase;
import com.todoList.app.domain.model.User;

@RestController
public class UserController {
    private final FindUserUseCase findUserUseCase;

    public UserController(
        FindUserUseCase findUserUseCase
    ) {
        this.findUserUseCase = findUserUseCase;
    }

    @GetMapping("/user")
    ResponseEntity<User> getUser(@RequestParam("userId") int userId) {
        User user = findUserUseCase.findUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    ResponseEntity<?> getUserList() {
        // TODO: implement this with a use case.
        return ResponseEntity.ok("OKAY");
    }

    @PostMapping("/user")
    ResponseEntity<?> createUser() {
        // TODO: implement this with a use case.
        return ResponseEntity.ok("OKAY");
    }

    @PatchMapping("/user")
    ResponseEntity<?> updateUser() {
        // TODO: implement this with a use case.
        return ResponseEntity.ok("OKAY");
    }
}
