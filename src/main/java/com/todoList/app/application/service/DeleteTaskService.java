package com.todoList.app.application.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.todoList.app.application.port.in.task.DeleteTaskUseCase;
import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.domain.exception.InvalidTaskException;

@Service
public class DeleteTaskService implements DeleteTaskUseCase {
    private final TaskRepository taskRepository;
    private final MessageSource messageSource;

    public DeleteTaskService(TaskRepository taskRepository, MessageSource messageSource) {
        this.taskRepository = taskRepository;
        this.messageSource = messageSource;
    }

    @Override
    public void delete(int id) throws InvalidTaskException {
        if (id <= 0) {
            String msg = messageSource.getMessage("error.task.invalid_id", null, Locale.getDefault());
            throw new InvalidTaskException(msg);
        }

        taskRepository.delete(id);
    }
}
