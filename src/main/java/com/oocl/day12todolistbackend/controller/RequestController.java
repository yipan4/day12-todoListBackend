package com.oocl.day12todolistbackend.controller;

import com.oocl.day12todolistbackend.entity.Todo;
import com.oocl.day12todolistbackend.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import org.springframework.web.bind.annotation.CrossOrigin;
//
//@CrossOrigin
@RestController
public class RequestController {
    @Autowired
    private TodoListService todoListService;

    public void resetData() {
        todoListService.resetData();
    }

    @PostMapping("/todos")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoListService.addTodo(todo);
    }

    @GetMapping("/todos")
    public List<Todo> getTodos() {
        return todoListService.getTodos();
    }
}
