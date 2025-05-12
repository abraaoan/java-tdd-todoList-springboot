package com.todoList.app.application.port.in.task;

import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

public interface FindTaskUseCase {
    Task findById(int id) throws InvalidTaskException;
}
