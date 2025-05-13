package com.todoList.app.adapter.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoList.app.application.port.in.task.FindTaskUseCase;
import com.todoList.app.application.port.in.task.ListTaskUseCase;
import com.todoList.app.domain.model.Task;

@RestController
public class TaskController {
    private final FindTaskUseCase findTaskUseCase;
    private final ListTaskUseCase listTaskUseCase;

    public TaskController(FindTaskUseCase findTaskUseCase, ListTaskUseCase listTaskUseCase) {
        this.findTaskUseCase = findTaskUseCase;
        this.listTaskUseCase = listTaskUseCase;
    }

    @GetMapping("/task")
    public ResponseEntity<?> getTask(@RequestParam("taskId") int taskId) {
        return ResponseEntity.ok(findTaskUseCase.findById(taskId));
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTaskList(@RequestParam("userId") int userId) {
        List<Task> taskList = listTaskUseCase.list(userId);

        return ResponseEntity.ok(taskList);
    }
}
