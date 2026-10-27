package com.example.minitaskspringboot.repository;

import com.example.minitaskspringboot.domain.SimpleTask;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    SimpleTask save(SimpleTask task);
    Optional<SimpleTask> findById(Long id);
    List<SimpleTask> findAll();
}