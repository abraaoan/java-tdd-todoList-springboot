package com.todoList.app.adapter.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user")
    ResponseEntity<?> getUser() {
        // TODO: implement this with a use case.
        return ResponseEntity.ok("OKAY");
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
