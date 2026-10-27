package com.example.minitaskspringboot.web;

import com.example.minitaskspringboot.domain.SimpleTask;
import com.example.minitaskspringboot.dto.CreateTaskDTO;
import com.example.minitaskspringboot.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControllerV2.class)
class TaskControllerV2Test {

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
        when(service.createTask(new CreateTaskDTO("Regar plantas")))
                .thenReturn(task(1L, "Regar plantas", false));

        mvc.perform(post("/v2/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Regar plantas\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/v2/tasks/")))
                .andExpect(jsonPath("$.title").value("Regar plantas"))
                .andExpect(jsonPath("$.done").value(false));
    }
}