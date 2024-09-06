package com.example.todolist.controller;

import com.example.todolist.Service.TodoService;
import com.example.todolist.model.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoItem>> getAllTodosForUser(@RequestParam Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<TodoItem> todos = todoService.getAllTodosForUser(userId);
        if (todos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            TodoItem todoItem = todoService.getTodoById(id).orElseThrow(() -> new NoSuchElementException("Todo not found"));
            return new ResponseEntity<>(todoItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TodoItem> markTodoAsComplete(@PathVariable Long id, @RequestBody TodoItem todoItem) {
        if (id == null || todoItem == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            TodoItem existingTodo = todoService.getTodoById(id).orElseThrow(() -> new NoSuchElementException("Todo not found"));
            existingTodo.setCompleted(todoItem.isCompleted());
            TodoItem updatedTodo = todoService.updateTodoItem(id, existingTodo);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        if (todoItem == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            TodoItem savedTodo = todoService.addTodoItem(todoItem);
            return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTodoItem(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            todoService.deleteTodoItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
