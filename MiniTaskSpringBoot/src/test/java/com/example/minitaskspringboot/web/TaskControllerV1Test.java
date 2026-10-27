package com.example.minitaskspringboot.web;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.dto.CreateTaskDTO;
import com.example.minitaskspringboot.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControllerV1.class)
class TaskControllerV1Test {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    TaskService service;

    private SimpleTask task(Long id, String title, boolean done) {
        SimpleTask task = new SimpleTask(title);
        task.setId(id);
        task.setDone(done);
        return task;
    }

    @Test
    void createsTaskWithLocation() throws Exception {
        when(service.createTask(new CreateTaskDTO("Pagar facturas")))
                .thenReturn(task(1L, "Pagar facturas", false));

        mvc.perform(post("/v1/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Pagar facturas\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/v1/tasks/")))
                .andExpect(jsonPath("$.title").value("Pagar facturas"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void listsCreatedTasks() throws Exception {
        when(service.listTasks()).thenReturn(List.of(task(1L, "Llamar al fontanero", false)));

        mvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", hasItem("Llamar al fontanero")));
    }
}