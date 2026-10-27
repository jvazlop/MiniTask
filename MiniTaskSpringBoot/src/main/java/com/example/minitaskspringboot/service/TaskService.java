package com.example.minitaskspringboot.service;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.domain.exceptions.TaskNotFoundException;
import com.example.minitaskspringboot.dto.CreateTaskDTO;
import com.example.minitaskspringboot.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;
    private long nextId = 1;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<SimpleTask> listTasks() {
        return repository.findAll();
    }

    public SimpleTask createTask(CreateTaskDTO dto) {
        // Versión inicial sin normalizar: se corregirá cuando exista el test que lo exija.
        SimpleTask task = new SimpleTask(dto.title());
        task.setId(nextId++);
        return repository.save(task);
    }

    public SimpleTask getTask(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}