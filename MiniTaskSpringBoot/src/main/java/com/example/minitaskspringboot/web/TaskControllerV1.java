package com.example.minitaskspringboot.web;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.dto.CreateTaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskControllerV1 {
    private final List<SimpleTask> tasks = new ArrayList<>();
    private long nextId = 1;

    @GetMapping
    public List<SimpleTask> list() {
        return tasks;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleTask> get(@PathVariable("id") Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SimpleTask> create(@RequestBody CreateTaskDTO dto) {
        SimpleTask task = new SimpleTask(dto.title());
        task.setId(nextId++);
        tasks.add(task);
        return ResponseEntity.created(URI.create("/v1/tasks/" + task.getId())).body(task);
    }
}