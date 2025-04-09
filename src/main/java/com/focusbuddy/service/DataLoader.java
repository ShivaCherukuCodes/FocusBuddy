package com.focusbuddy.service;

import com.focusbuddy.model.Todo;
import com.focusbuddy.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final TodoRepository todoRepository;

    public DataLoader(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... args) {
        if (todoRepository.count() == 0) {
            Todo t1 = new Todo(null, "Gym", "Leg day workout", false, null);
            todoRepository.save(t1);
            System.out.println("✅ Sample data loaded.");
        } else {
            System.out.println("ℹ️ Existing data found, skipping sample data load.");
        }
    }
}
