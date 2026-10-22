package com.example.minitaskspringboot.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControllerV2.class)
class TaskControllerV2Test {
    @Autowired MockMvc mvc;

    @Test
    void createsTaskWithLocation() throws Exception {
        mvc.perform(post("/v2/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Regar plantas\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/v2/tasks/")))
                .andExpect(jsonPath("$.title").value("Regar plantas"))
                .andExpect(jsonPath("$.done").value(false));
    }
}