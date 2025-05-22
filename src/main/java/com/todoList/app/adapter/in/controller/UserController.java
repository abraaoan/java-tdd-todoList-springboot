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
import com.todoList.app.adapter.in.controller.dto.LoginRequest;
import com.todoList.app.adapter.in.controller.dto.LoginResponse;
import com.todoList.app.adapter.in.controller.dto.UpdateUserRequest;
import com.todoList.app.adapter.in.controller.dto.UserResponse;
import com.todoList.app.adapter.in.controller.mapper.UserMapper;
import com.todoList.app.application.port.in.user.CreateUserUseCase;
import com.todoList.app.application.port.in.user.DeleteUserUseCase;
import com.todoList.app.application.port.in.user.FindUserUseCase;
import com.todoList.app.application.port.in.user.ListUserUseCase;
import com.todoList.app.application.port.in.user.LoginUseCase;
import com.todoList.app.application.port.in.user.UpdateUserUseCase;
import com.todoList.app.domain.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User", description = "Lista de request dos users.")
@RestController
public class UserController {
    private final FindUserUseCase findUserUseCase;
    private final CreateUserUseCase createUserUsecase;
    private final ListUserUseCase listUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final LoginUseCase loginUseCase;

    public UserController(
        FindUserUseCase findUserUseCase,
        CreateUserUseCase createUserUsecase,
        ListUserUseCase listUserUseCase,
        UpdateUserUseCase updateUserUseCase,
        DeleteUserUseCase deleteUserUseCase,
        LoginUseCase loginUseCase
    ) {
        this.findUserUseCase = findUserUseCase;
        this.createUserUsecase = createUserUsecase;
        this.listUserUseCase = listUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @Operation(summary = "Retorna um user baseada no id.")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> getUser(@RequestParam("userId") int userId) {
        User user = findUserUseCase.findUser(userId);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @Operation(summary = "Retorna a lista de users.")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserResponse>> getUserList() {
        List<User> users = listUserUseCase.listUsers();
        List<UserResponse> response = users.stream().map(UserMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Cria um usuário.")
    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User newUser = createUserUsecase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(newUser));
    }

    @Operation(summary = "Atualiza um usuário.")
    @PatchMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        User updatedUser = updateUserUseCase.updateUser(request);
        return ResponseEntity.ok(UserMapper.toResponse(updatedUser));
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "Apaga um usuário.")
    @DeleteMapping("/user")
    ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequest request) {
        deleteUserUseCase.deleteUser(request.getUserId());
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Faz o login dp usuário.")
    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = loginUseCase.login(request.getEmail(), request.getPassword());
        LoginResponse response = new LoginResponse(token, "Bearer");
        return ResponseEntity.ok(response);
    }
}
