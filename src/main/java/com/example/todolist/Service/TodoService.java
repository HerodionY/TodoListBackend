package com.example.todolist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.todolist.Repository.TodoItemRepository;
import com.example.todolist.model.TodoItem;
import com.example.todolist.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    public List<TodoItem> getAllTodosForUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return todoItemRepository.findByUserId(userId);
    }

    public Optional<TodoItem> getTodoById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return todoItemRepository.findById(id);
    }

    public TodoItem saveTodoItem(TodoItem todoItem) {
        if (todoItem == null) {
            throw new IllegalArgumentException("TodoItem cannot be null");
        }
        return todoItemRepository.save(todoItem);
    }

    public TodoItem addTodoItem(TodoItem todoItem) {
        if (todoItem == null) {
            throw new IllegalArgumentException("TodoItem cannot be null");
        }
        return todoItemRepository.save(todoItem);
    }

    public void deleteTodoItem(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        todoItemRepository.deleteById(id);
    }

    public TodoItem updateTodoItem(Long id, TodoItem newTodoItem) {
        if (id == null || newTodoItem == null) {
            throw new IllegalArgumentException("ID or TodoItem cannot be null");
        }
        TodoItem todo = todoItemRepository.findById(id).orElseThrow();
        todo.setTitle(newTodoItem.getTitle());
        todo.setCompleted(newTodoItem.isCompleted());
        return todoItemRepository.save(todo);
    }

    public TodoItem updateTodoCompletionStatus(Long id, boolean completed) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        TodoItem todo = todoItemRepository.findById(id).orElseThrow();
        todo.setCompleted(completed);
        return todoItemRepository.save(todo);
    }
}
