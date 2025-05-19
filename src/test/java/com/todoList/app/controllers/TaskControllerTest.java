package com.todoList.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.todoList.app.adapter.in.controller.TaskController;
import com.todoList.app.application.port.in.task.CreateTaskUseCase;
import com.todoList.app.application.port.in.task.DeleteTaskUseCase;
import com.todoList.app.application.port.in.task.FindTaskUseCase;
import com.todoList.app.application.port.in.task.ListTaskUseCase;
import com.todoList.app.application.port.in.task.UpdateTaskUseCase;
import com.todoList.app.domain.model.Task;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateTaskUseCase createTaskUseCase;

    @MockBean
    private DeleteTaskUseCase deleteTaskUseCase;

    @MockBean
    private FindTaskUseCase findTaskUseCase;

    @MockBean
    private ListTaskUseCase listTaskUseCase;

    @MockBean
    private UpdateTaskUseCase updateTaskUseCase;

    @Test
    void shouldCrateTaskSuccessfully() throws Exception {
        // Arrange
        Task task = new Task(1, "new task", false, 1);
        when(createTaskUseCase.create(any(String.class), any(int.class)))
                .thenReturn(task);

        String payload = "{\"title\": \"new task\", \"userID\":1}";

        // Act & Assert
        mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.title").value("new task"));
    }

    @Test
    void shouldGetTaskSuccessFully() throws Exception {
        // Arrange
        Task task = new Task(1, "new task", false, 1);
        when(findTaskUseCase.findById(any(int.class)))
                .thenReturn(task);

        // Act & Assert
        mockMvc.perform(get("/task?taskId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("new task"));
    }

    @Test
    void shouldListTasksSuccessfully() throws Exception {
        // Arrange
        when(listTaskUseCase.list(any(int.class))).thenReturn(List.of(
                new Task(1, "title #1", false, 1),
                new Task(2, "title #2", false, 1),
                new Task(3, "title #3", false, 1)));

        // Act & Assert
        mockMvc.perform(get("/tasks?userId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].title").value("title #2"))
                .andExpect(jsonPath("$[2].id").value(3));
    }

    @Test
    void shouldUpdateTaskSuccessfully() throws Exception {
        // Arrange
        String payload = "{\"id\": 1, \"title\": \"update task\", \"completed\": true, \"userId\": 1}";

        // Act & Assert
        mockMvc.perform(patch("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("update task"))
                .andExpect(jsonPath("$.status").value(true));

    }

    @Test
    void shouldDeleteTaskSuccessfully() throws Exception {
        // Arrange
        String payload = "{\"taskId\": 5}";

        // Act & Assert
        mockMvc.perform(delete("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk());
    }
}
