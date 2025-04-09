package com.focusbuddy.controller;

import com.focusbuddy.dto.TodoDTO;
import com.focusbuddy.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        TodoDTO todoDTO = todoService.getTodoById(id);
        if (todoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<TodoDTO> addTodo(@Valid @RequestBody TodoDTO todoDTO) {
        TodoDTO savedTodo = todoService.addTodo(todoDTO);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TodoDTO> updateTodo(@Valid @RequestBody TodoDTO todoDTO) {
        TodoDTO updated = todoService.updateTodo(todoDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoDTO>> getCompletedTodos() {
        return ResponseEntity.ok(todoService.getTodosByStatus(true));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TodoDTO>> getPendingTodos() {
        return ResponseEntity.ok(todoService.getTodosByStatus(false));
    }
}
