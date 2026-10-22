package com.example.minitaskspringboot.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControllerV1.class)
class TaskControllerV1Test {
    @Autowired MockMvc mvc;

    @Test
    void createsTaskWithLocation() throws Exception {
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
        mvc.perform(post("/v1/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Llamar al fontanero\"}"))
                .andExpect(status().isCreated());

        mvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", hasItem("Llamar al fontanero")));
    }
}