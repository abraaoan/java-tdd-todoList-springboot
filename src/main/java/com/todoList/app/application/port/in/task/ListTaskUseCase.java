package com.todoList.app.application.port.in.task;

import java.util.List;

import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

public interface ListTaskUseCase {
    List<Task>list(int userID) throws InvalidTaskException;
}
