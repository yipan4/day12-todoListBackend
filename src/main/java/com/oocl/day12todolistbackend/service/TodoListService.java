package com.oocl.day12todolistbackend.service;

import com.oocl.day12todolistbackend.dto.PostTodoReq;
import com.oocl.day12todolistbackend.dto.PutTodoReq;
import com.oocl.day12todolistbackend.entity.Todo;
import com.oocl.day12todolistbackend.exception.TodoListEmptyTextException;
import com.oocl.day12todolistbackend.exception.TodoListMissingTextException;
import com.oocl.day12todolistbackend.exception.TodoNotFoundException;
import com.oocl.day12todolistbackend.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {
    @Autowired
    private TodoListRepository todoListRepository;

    public void resetData() {
        todoListRepository.resetData();
    }

    public Todo addTodo(PostTodoReq todo) {
        if (todo.getText() == null) {
            throw new TodoListMissingTextException("Todo text is required");
        }
        if (todo.getText().isEmpty()) {
            throw new TodoListEmptyTextException("Todo text cannot be empty");
        }
        return todoListRepository.addTodo(todo);
    }

    public List<Todo> getTodos() {
        return todoListRepository.getTodos();
    }

    public Todo updateTodo(int id, PutTodoReq todo) {
//        Todo existingTodo = todoListRepository.findTodoById(id);
//        if (existingTodo == null) {
//            throw new TodoNotFoundException("Todo with id " + id + " not found");
//        }
        todo.setId(id);
        return todoListRepository.updateTodo(todo);
    }

    public Todo findTodoById(int id) {
        Todo foundTodo = todoListRepository.findTodoById(id);
        if (foundTodo == null) {
            throw new TodoNotFoundException("Todo with id " + id + " not found");
        }
        return foundTodo;
    }
}
