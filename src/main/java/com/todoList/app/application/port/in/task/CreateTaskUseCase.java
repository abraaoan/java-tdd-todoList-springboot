package com.todoList.app.application.port.in.task;

import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

public interface CreateTaskUseCase {
    Task create(String title, int userID) throws InvalidTaskException;
}
