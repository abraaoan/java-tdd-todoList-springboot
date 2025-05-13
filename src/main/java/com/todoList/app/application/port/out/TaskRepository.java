package com.todoList.app.application.port.out;

import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;
import java.util.List;

public interface TaskRepository {
    Task save(Task task);
    List<Task> list(int userID);
    Task findById(int id) throws InvalidTaskException;
    void delete(int id);
    void update(Task task);
}
