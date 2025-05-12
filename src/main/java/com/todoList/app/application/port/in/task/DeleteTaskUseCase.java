package com.todoList.app.application.port.in.task;

import com.todoList.app.domain.exception.InvalidTaskException;

public interface DeleteTaskUseCase {
    void delete(int id) throws InvalidTaskException;
}
