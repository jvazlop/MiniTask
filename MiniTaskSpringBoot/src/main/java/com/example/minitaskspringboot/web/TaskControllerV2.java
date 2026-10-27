package com.example.minitaskspringboot.web;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.dto.CreateTaskDTO;
import com.example.minitaskspringboot.service.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v2/tasks")
public class TaskControllerV2 {

    private final TaskService service;

    public TaskControllerV2(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<SimpleTask> list() {
        return service.listTasks();
    }

    @PostMapping
    public ResponseEntity<SimpleTask> create(@RequestBody CreateTaskDTO dto) {
        SimpleTask created = service.createTask(dto);
        return ResponseEntity.created(URI.create("/v2/tasks/" + created.getId())).body(created);
    }
}