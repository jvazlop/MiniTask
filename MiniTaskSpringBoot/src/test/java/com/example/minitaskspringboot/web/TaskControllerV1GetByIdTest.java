package com.example.minitaskspringboot.web;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.domain.exceptions.TaskNotFoundException;
import com.example.minitaskspringboot.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControllerV1.class)
class TaskControllerV1GetByIdTest {

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
    void getExistingTask() throws Exception {
        when(service.getTask(1L)).thenReturn(task(1L, "Comprar leche", false));

        mvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Comprar leche"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void getMissingTaskReturns404() throws Exception {
        when(service.getTask(999L)).thenThrow(new TaskNotFoundException(999L));

        mvc.perform(get("/v1/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}