package com.example.task_manager_api.controller;

import com.example.task_manager_api.dto.TaskDTO;
import com.example.task_manager_api.model.Task;
import com.example.task_manager_api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void createTask_returnsCreatedTask() throws Exception {
        TaskDTO dto = new TaskDTO("Test title", "Test description", false);

        Task task = new Task();
        task.setId(1L);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test title")));
    }

    @Test
    void getAllTasks_returnsList() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Sample");
        task.setDescription("Sample desc");
        task.setCompleted(false);

        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Sample")));
    }

    @Test
    void updateTask_updatesAndReturnsTask() throws Exception {
        Long id = 1L;

        Task updateRequest = new Task();
        updateRequest.setTitle("Updated");
        updateRequest.setDescription("Updated desc");
        updateRequest.setCompleted(true);

        Task updatedTask = new Task();
        updatedTask.setId(id);
        updatedTask.setTitle("Updated");
        updatedTask.setDescription("Updated desc");
        updatedTask.setCompleted(true);

        when(taskService.updateTask(eq(id), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated")));
    }
}
