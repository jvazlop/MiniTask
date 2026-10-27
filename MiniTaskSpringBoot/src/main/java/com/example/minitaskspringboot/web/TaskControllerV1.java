package com.example.minitaskspringboot.web;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.domain.exceptions.TaskNotFoundException;
import com.example.minitaskspringboot.dto.CreateTaskDTO;
import com.example.minitaskspringboot.service.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskControllerV1 {

    private final TaskService service;

    public TaskControllerV1(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<SimpleTask> list() {
        return service.listTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleTask> get(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.getTask(id));
        } catch (TaskNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SimpleTask> create(@RequestBody CreateTaskDTO dto) {
        SimpleTask created = service.createTask(dto);
        return ResponseEntity.created(URI.create("/v1/tasks/" + created.getId())).body(created);
    }
}