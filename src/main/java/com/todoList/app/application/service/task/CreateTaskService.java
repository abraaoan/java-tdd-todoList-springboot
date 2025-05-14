package com.todoList.app.application.service.task;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.todoList.app.application.port.in.task.CreateTaskUseCase;
import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

@Service
public class CreateTaskService implements CreateTaskUseCase {
    private final TaskRepository taskRepository;
    private final MessageSource messageSource;

    public CreateTaskService(TaskRepository taskRepository, MessageSource messageSource) {
        this.taskRepository = taskRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Task create(String title, int userID) {
        if (title == null || title.isBlank()) {
            String msg = messageSource.getMessage("error.task.invalid_title", null, Locale.getDefault());
            throw new InvalidTaskException(msg);
        }

        if (userID <= 0) {
            String msg = messageSource.getMessage("error.user.not_found", null, Locale.getDefault());
            throw new InvalidTaskException(msg);
        }

        Task task = new Task(0, title, false, userID);
        return taskRepository.save(task);
    }
}
