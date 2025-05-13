package com.todoList.app.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todoList.app.infrastructure.persistence.entity.TaskEntity;

import java.util.List;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, Integer> {
    List<TaskEntity> findByUserId(int userId);
    List<TaskEntity> findByTitleContainingIgnoreCaseAndUserId(String titleFragment, int userId);
}