package com.focusbuddy.service;


import com.focusbuddy.controller.ToDoController;
import com.focusbuddy.dto.TodoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    private TodoDTO todoDTO;

    @BeforeEach
    void setUp() {
        todoDTO = new TodoDTO(1L, "Test Task", "Test Description", false, LocalDateTime.now());
    }

    @Test
    public void testGetAllTodos() throws Exception {
        Mockito.when(todoService.getAllTodos()).thenReturn(List.of(todoDTO));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    public void testGetTodoById() throws Exception {
        Mockito.when(todoService.getTodoById(1L)).thenReturn(todoDTO);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }


    @Test
    public void testGetCompletedTodos() throws Exception {
        Mockito.when(todoService.getTodosByStatus(true)).thenReturn(List.of(todoDTO));

        mockMvc.perform(get("/api/todos/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(false)); // Based on mock
    }

    @Test
    public void testGetPendingTodos() throws Exception {
        Mockito.when(todoService.getTodosByStatus(false)).thenReturn(List.of(todoDTO));

        mockMvc.perform(get("/api/todos/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(false));
    }
}
