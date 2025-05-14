package com.todoList.app.application.service.task;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.todoList.app.application.port.in.task.ListTaskUseCase;
import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;

@Service
public class ListTaskService implements ListTaskUseCase {
    private final TaskRepository taskRepository;
    private final MessageSource messageSource;

    public ListTaskService(TaskRepository taskRepository, MessageSource messageSource) {
        this.taskRepository = taskRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<Task> list(int userID) throws InvalidTaskException {
        if (userID <= 0) {
            String msg = messageSource.getMessage("error.user.not_found", null, Locale.getDefault());
            throw new InvalidTaskException(msg);
        }

        return taskRepository.list(userID);
    }
    
}
