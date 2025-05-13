package com.todoList.app.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todoList.app.infrastructure.persistence.entity.UserEntity;

public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {}
