package com.example.minitaskspringboot.web;

import com.example.minitaskspringboot.domain.SimpleTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControllerV1.class)
class TaskControllerV1GetByIdTest {
    @Autowired MockMvc mvc;

    @Test
    void getExistingTask() throws Exception {
        String location = mvc.perform(post("/v1/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Comprar leche\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Comprar leche"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void getMissingTaskReturns404() throws Exception {
        mvc.perform(get("/v1/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}