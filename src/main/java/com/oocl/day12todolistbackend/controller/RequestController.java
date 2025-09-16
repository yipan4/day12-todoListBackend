package com.oocl.day12todolistbackend.controller;

import com.oocl.day12todolistbackend.dto.PostTodoReq;
import com.oocl.day12todolistbackend.dto.PutTodoReq;
import com.oocl.day12todolistbackend.entity.Todo;
import com.oocl.day12todolistbackend.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
public class RequestController {
    @Autowired
    private TodoListService todoListService;

    public void resetData() {
        todoListService.resetData();
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> addTodo(@RequestBody PostTodoReq todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoListService.addTodo(todo));
    }

    @GetMapping("/todos")
    public List<Todo> getTodos() {
        return todoListService.getTodos();
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable int id, @RequestBody PutTodoReq todo) {
        return ResponseEntity.status(HttpStatus.OK).body(todoListService.updateTodo(id, todo));
    }

}
