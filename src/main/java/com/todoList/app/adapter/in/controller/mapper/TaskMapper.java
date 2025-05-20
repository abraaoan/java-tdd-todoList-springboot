package com.todoList.app.adapter.in.controller.mapper;

import com.todoList.app.adapter.in.controller.dto.TaskResponse;
import com.todoList.app.domain.model.Task;

public class TaskMapper {
    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getUserID());
    }
}
