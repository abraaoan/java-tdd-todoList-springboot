package com.todoList.app.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todoList.app.domain.model.Task;

@SpringBootTest
public class TaskRepositoryAdapterTest {
    @Autowired
    private TaskRepositoryAdapter taskRepositoryAdapter;

    @Test
    void shouldSaveTaskAndRetrieveIt() {
        // Arrange
        Task task = new Task(1, "new task", false, 1);

        // Act
        Task saved = taskRepositoryAdapter.save(task);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("new task", saved.getTitle());

    }
}
