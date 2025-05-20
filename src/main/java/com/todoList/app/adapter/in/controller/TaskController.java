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

import com.todoList.app.adapter.in.controller.dto.CreateTaskRequest;
import com.todoList.app.adapter.in.controller.dto.DeleteTaskRequest;
import com.todoList.app.adapter.in.controller.dto.TaskResponse;
import com.todoList.app.adapter.in.controller.dto.UpdateTaskRequest;
import com.todoList.app.adapter.in.controller.mapper.TaskMapper;
import com.todoList.app.application.port.in.task.CreateTaskUseCase;
import com.todoList.app.application.port.in.task.DeleteTaskUseCase;
import com.todoList.app.application.port.in.task.FindTaskUseCase;
import com.todoList.app.application.port.in.task.ListTaskUseCase;
import com.todoList.app.application.port.in.task.UpdateTaskUseCase;
import com.todoList.app.domain.model.Task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tasks", description = "Lista de request para gerenciar as tasks de um user.")
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
            DeleteTaskUseCase deleteTaskUseCase) {
        this.findTaskUseCase = findTaskUseCase;
        this.listTaskUseCase = listTaskUseCase;
        this.createTaskUseCase = createTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
    }

    @Operation(summary = "Retorna uma tarefa baseada no id.")
    @GetMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> getTask(@RequestParam("taskId") int taskId) {
        Task task = findTaskUseCase.findById(taskId);
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @Operation(summary = "Retorna uma lista de tarefas baseada no user id.")
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskResponse>> getTaskList(@RequestParam("userId") int userId) {
        List<Task> tasks = listTaskUseCase.list(userId);
        List<TaskResponse> responses = tasks.stream()
                .map(TaskMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Criar uma nova tarefa.")
    @PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        Task task = createTaskUseCase.create(request.getTitle(), request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskMapper.toResponse(task));
    }

    @Operation(summary = "Atualiza uma tarefa.")
    @PatchMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskRequest request) {
        Task task = new Task(
                request.getId(),
                request.getTitle(),
                request.isCompleted(),
                request.getUserId());

        updateTaskUseCase.update(task);

        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "Apaga uma tarefa.")
    @DeleteMapping("/task")
    public ResponseEntity<String> deleteTask(@RequestBody DeleteTaskRequest request) {
        deleteTaskUseCase.delete(request.getTaskId());

        return ResponseEntity.noContent().build();
    }
}
