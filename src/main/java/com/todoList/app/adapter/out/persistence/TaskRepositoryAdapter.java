package com.todoList.app.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.todoList.app.application.port.out.TaskRepository;
import com.todoList.app.domain.exception.InvalidTaskException;
import com.todoList.app.domain.model.Task;
import com.todoList.app.infrastructure.persistence.JpaTaskRepository;
import com.todoList.app.infrastructure.persistence.entity.TaskEntity;
import com.todoList.app.infrastructure.persistence.entity.UserEntity;

@Component
public class TaskRepositoryAdapter implements TaskRepository {
    private final JpaTaskRepository jpaTaskRepository;

    public TaskRepositoryAdapter(JpaTaskRepository jpaTaskRepository) {
        this.jpaTaskRepository = jpaTaskRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = mapToEntity(task);
        TaskEntity saved = jpaTaskRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public List<Task> list(int userID) {
        List<TaskEntity> tasks = jpaTaskRepository.findByUserId(userID);
        return tasks.stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public Task findById(int id) {
        Task task = jpaTaskRepository.findById(id)
                .map(this::mapToDomain)
                .orElseThrow(() -> new InvalidTaskException("Tarefa com ID " + id + " não encontrada"));

        return task;
    }

    @Override
    public void delete(int id) {
        jpaTaskRepository.deleteById(id);
    }

    @Override
    public void update(Task task) {
        TaskEntity entity = mapToEntity(task);
        jpaTaskRepository.save(entity); // `save` faz update se o ID já existir
    }

    private TaskEntity mapToEntity(Task task) {
        UserEntity user = new UserEntity();
        user.setId(task.getUserID());

        TaskEntity entity = new TaskEntity(
                task.getId(),
                task.getTitle(),
                user,
                task.getStatus());

        return entity;
    }

    private Task mapToDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.isCompleted(),
                entity.getUser().getId());
    }
}
