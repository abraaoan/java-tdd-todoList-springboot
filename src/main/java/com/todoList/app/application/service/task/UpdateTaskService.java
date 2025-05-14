package com.todoList.app.application.service.task;

import org.springframework.stereotype.Service;

import com.todoList.app.application.port.in.task.UpdateTaskUseCase;
import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

@Service
public class UpdateTaskService implements UpdateTaskUseCase {
    private final TaskRepository taskRepository;;

    public UpdateTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void update(Task task) throws InvalidTaskException {
        taskRepository.update(task);   
    }
}
