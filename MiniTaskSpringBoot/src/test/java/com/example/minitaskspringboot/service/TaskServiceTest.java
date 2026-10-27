package com.example.minitaskspringboot.service;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.dto.CreateTaskDTO;
import com.example.minitaskspringboot.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskService service;

    @Test
    void createStripsTitleEdges() {
        when(repository.save(any(SimpleTask.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SimpleTask result = service.createTask(new CreateTaskDTO("  Repasar Spring Boot  "));

        assertThat(result.getTitle()).isEqualTo("Repasar Spring Boot");
        assertThat(result.isDone()).isFalse();
    }

    @Test
    void createKeepsAlreadyCleanTitle() {
        when(repository.save(any(SimpleTask.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SimpleTask result = service.createTask(new CreateTaskDTO("Repasar Spring Boot"));

        assertThat(result.getTitle()).isEqualTo("Repasar Spring Boot");
        assertThat(result.isDone()).isFalse();
    }
}