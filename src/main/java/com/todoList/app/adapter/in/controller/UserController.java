package com.todoList.app.adapter.in.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoList.app.adapter.in.controller.dto.CreateUserRequest;
import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.in.user.FindUserUseCase;
import com.todoList.app.application.port.in.user.ListUserUseCase;
import com.todoList.app.application.port.in.user.UpdateUserUseCase;
import com.todoList.app.domain.model.User;

@RestController
public class UserController {
    private final FindUserUseCase findUserUseCase;
    private final CreateUserUseCase createUserUsecase;
    private final ListUserUseCase listUserUseCase;
    private final UpdateUserUseCase updateUserUsecase;

    public UserController(
        FindUserUseCase findUserUseCase,
        CreateUserUseCase createUserUsecase,
        ListUserUseCase listUserUseCase,
        UpdateUserUseCase updateUserUsecase
    ) {
        this.findUserUseCase = findUserUseCase;
        this.createUserUsecase = createUserUsecase;
        this.listUserUseCase = listUserUseCase;
        this.updateUserUsecase = updateUserUsecase;
    }

    @GetMapping("/user")
    ResponseEntity<User> getUser(@RequestParam("userId") int userId) {
        User user = findUserUseCase.findUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    ResponseEntity<?> getUserList() {
        List<User> users = listUserUseCase.listUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/user")
    ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        User newUser = createUserUsecase.createUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @PatchMapping("/user")
    ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request) {
        User user = new User(request.getId(), request.getEmail(), request.getPassword());
        User updatedUser = updateUserUsecase.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }
}
