package com.focusbuddy.service;

import com.focusbuddy.dto.TodoDTO;
import com.focusbuddy.model.Todo;
import com.focusbuddy.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo;
    private TodoDTO todoDTO;

    @BeforeEach
    public void setup() {
        LocalDateTime now = LocalDateTime.now();
        todo = new Todo(1L, "Test Task", "Test Description", false, now);
        todoDTO = new TodoDTO(1L, "Test Task", "Test Description", false, now);
    }

    @Test
    public void testGetAllTodos_Positive() {
        when(todoRepository.findAll()).thenReturn(List.of(todo));
        List<TodoDTO> todos = todoService.getAllTodos();
        assertEquals(1, todos.size());
        assertEquals("Test Task", todos.get(0).getTitle());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    public void testGetTodoById_Positive() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        TodoDTO result = todoService.getTodoById(1L);
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTodoById_Negative() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());
        TodoDTO result = todoService.getTodoById(99L);
        assertNull(result);
        verify(todoRepository, times(1)).findById(99L);
    }

    @Test
    public void testAddTodo_Positive() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        TodoDTO saved = todoService.addTodo(todoDTO);
        assertNotNull(saved);
        assertEquals("Test Task", saved.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    public void testUpdateTodo_Positive() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        TodoDTO updated = todoService.updateTodo(todoDTO);
        assertEquals("Test Task", updated.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    public void testGetTodosByStatus_Completed() {
        when(todoRepository.findByCompleted(true)).thenReturn(List.of(todo));
        List<TodoDTO> completedTodos = todoService.getTodosByStatus(true);
        assertFalse(completedTodos.isEmpty());
        assertEquals("Test Task", completedTodos.get(0).getTitle());
        verify(todoRepository, times(1)).findByCompleted(true);
    }

    @Test
    public void testDeleteTodo_Positive() {
        doNothing().when(todoRepository).deleteById(1L);
        todoService.deleteTodo(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }
}
