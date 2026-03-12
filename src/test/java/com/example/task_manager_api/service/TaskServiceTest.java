package com.example.task_manager_api.service;

import com.example.task_manager_api.dto.TaskDTO;
import com.example.task_manager_api.model.Task;
import com.example.task_manager_api.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;
    @Test
    void createTask_savesAndReturnsTask() {
        TaskDTO dto = new TaskDTO("Test title", "Test description", false);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle(dto.getTitle());
        savedTask.setDescription(dto.getDescription());
        savedTask.setCompleted(dto.isCompleted());

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(dto);

        verify(taskRepository, times(1)).save(any(Task.class));
        assertNotNull(result);
        assertEquals("Test title", result.getTitle());
    }

    @Test
    void updateTask_updatesFieldsAndSaves() {
        Long id = 1L;

        Task existingTask = new Task();
        existingTask.setId(id);
        existingTask.setTitle("Old title");
        existingTask.setDescription("Old description");
        existingTask.setCompleted(false);

        Task updateRequest = new Task();
        updateRequest.setTitle("New title");
        updateRequest.setDescription("New description");
        updateRequest.setCompleted(true);

        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.updateTask(id, updateRequest);

        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, times(1)).save(existingTask);

        assertEquals("New title", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertEquals(true, result.isCompleted());
    }
}