package com.todoList.app.adapter.in.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoList.app.adapter.in.controller.dto.CreateUserRequest;
import com.todoList.app.adapter.in.controller.dto.DeleteUserRequest;
import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.in.user.DeleteUserUseCase;
import com.todoList.app.application.port.in.user.FindUserUseCase;
import com.todoList.app.application.port.in.user.ListUserUseCase;
import com.todoList.app.application.port.in.user.UpdateUserUseCase;
import com.todoList.app.domain.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "Lista de request dos users.")
@RestController
public class UserController {
    private final FindUserUseCase findUserUseCase;
    private final CreateUserUseCase createUserUsecase;
    private final ListUserUseCase listUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(
        FindUserUseCase findUserUseCase,
        CreateUserUseCase createUserUsecase,
        ListUserUseCase listUserUseCase,
        UpdateUserUseCase updateUserUseCase,
        DeleteUserUseCase deleteUserUseCase
    ) {
        this.findUserUseCase = findUserUseCase;
        this.createUserUsecase = createUserUsecase;
        this.listUserUseCase = listUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @Operation(summary = "Retorna um user baseada no id.")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> getUser(@RequestParam("userId") int userId) {
        User user = findUserUseCase.findUser(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Retorna a lista de users.")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<User>> getUserList() {
        List<User> users = listUserUseCase.listUsers();
        return ResponseEntity.ok(users);
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Cria um usuário.")
    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User newUser = createUserUsecase.createUser(request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "Atualiza um usuário.")
    @PatchMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> updateUser(@RequestBody UpdateUserRequest request) {
        User user = new User(request.getId(), request.getEmail(), request.getPassword());
        User updatedUser = updateUserUseCase.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "Apaga um usuário.")
    @DeleteMapping("/user")
    ResponseEntity deleteUser(@RequestBody DeleteUserRequest request) {
        deleteUserUseCase.deleteUser(request.getUserId());
        return ResponseEntity.noContent().build();
    }
}
