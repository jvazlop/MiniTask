package com.example.minitaskspringboot.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {

    @Test
    void newTaskShouldNotBeCompleted() {
        var task = new Task("Comprar leche");
        assertThat(task.isCompleted()).isFalse();
    }
}