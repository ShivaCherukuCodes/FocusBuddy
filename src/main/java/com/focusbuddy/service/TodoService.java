package com.focusbuddy.service;

import com.focusbuddy.dto.TodoDTO;
import com.focusbuddy.model.Todo;
import com.focusbuddy.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TodoDTO getTodoById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return todo.map(this::convertToDTO).orElse(null);
    }

    public TodoDTO addTodo(TodoDTO todoDTO) {
        Todo saved = todoRepository.save(convertToEntity(todoDTO));
        return convertToDTO(saved);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoDTO updateTodo(TodoDTO dto) {
        Todo updated = todoRepository.save(convertToEntity(dto));
        return convertToDTO(updated);
    }

    public List<TodoDTO> getTodosByStatus(boolean completed) {
        return todoRepository.findByCompleted(completed).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Mapping methods
    private TodoDTO convertToDTO(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.isCompleted());
        dto.setDueDate(todo.getDueDate());
        return dto;
    }

    private Todo convertToEntity(TodoDTO dto) {
        Todo todo = new Todo();
        todo.setId(dto.getId());
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCompleted(dto.isCompleted());
        todo.setDueDate(dto.getDueDate());
        return todo;
    }

}
