package com.example.minitaskspringboot.repository;

import com.example.minitaskspringboot.domain.SimpleTask;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class InMemoryTaskRepository implements TaskRepository {

    private final List<SimpleTask> tasks = new ArrayList<>();

    @Override
    public SimpleTask save(SimpleTask task) {
        tasks.removeIf(existing -> Objects.equals(existing.getId(), task.getId()));
        tasks.add(task);
        return task;
    }

    @Override
    public Optional<SimpleTask> findById(Long id) {
        return tasks.stream()
                .filter(task -> Objects.equals(task.getId(), id))
                .findFirst();
    }

    @Override
    public List<SimpleTask> findAll() {
        return new ArrayList<>(tasks);
    }
}