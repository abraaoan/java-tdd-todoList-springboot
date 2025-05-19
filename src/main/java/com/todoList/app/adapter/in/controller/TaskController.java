package com.todoList.app.adapter.in.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoList.app.adapter.in.controller.dto.CreateTaskRequest;
import com.todoList.app.adapter.in.controller.dto.DeleteTaskRequest;
import com.todoList.app.adapter.in.controller.dto.UpdateTaskRequest;
import com.todoList.app.application.port.in.task.CreateTaskUseCase;
import com.todoList.app.application.port.in.task.DeleteTaskUseCase;
import com.todoList.app.application.port.in.task.FindTaskUseCase;
import com.todoList.app.application.port.in.task.ListTaskUseCase;
import com.todoList.app.application.port.in.task.UpdateTaskUseCase;
import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

@RestController
public class TaskController {
    private final FindTaskUseCase findTaskUseCase;
    private final ListTaskUseCase listTaskUseCase;
    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    public TaskController(
        FindTaskUseCase findTaskUseCase,
        ListTaskUseCase listTaskUseCase,
        CreateTaskUseCase createTaskUseCase,
        UpdateTaskUseCase updateTaskUseCase,
        DeleteTaskUseCase deleteTaskUseCase
    ) {
        this.findTaskUseCase = findTaskUseCase;
        this.listTaskUseCase = listTaskUseCase;
        this.createTaskUseCase = createTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
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

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request) {
        Task task = createTaskUseCase.create(request.getTitle(), request.getUserId());
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/task")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequest request) {
        Task task = new Task(
            request.getId(), 
            request.getTitle(), 
            request.isCompleted(), 
            request.getUserId()
        );

        try {
            updateTaskUseCase.update(task);
        } catch (InvalidTaskException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/task")
    public ResponseEntity<?> deleteTask(@RequestBody DeleteTaskRequest request) {
        deleteTaskUseCase.delete(request.getTaskId());

        return ResponseEntity.ok("Task deleted.");
    }
}
