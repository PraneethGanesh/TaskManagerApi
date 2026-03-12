package com.example.task_manager_api.service;

import com.example.task_manager_api.dto.TaskDTO;
import com.example.task_manager_api.exception.TaskNotFoundException;
import com.example.task_manager_api.model.Task;
import com.example.task_manager_api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public Task createTask(TaskDTO dto) {

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());

        return repository.save(task);
    }

    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public Task getTaskById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task updateTask(Long id, Task updatedTask){
        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());

        return repository.save(task);
    }

    public void deleteTask(Long id){
        repository.deleteById(id);
    }
}
