package com.oocl.day12todolistbackend.service;

import com.oocl.day12todolistbackend.entity.Todo;
import com.oocl.day12todolistbackend.exception.TodoListEmptyTextException;
import com.oocl.day12todolistbackend.exception.TodoListMissingTextException;
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

    public Todo addTodo(Todo todo) {
        if (todo.getText().isEmpty()) {
            throw new TodoListEmptyTextException("Todo text cannot be empty");
        }
//        if (todo.getText() == null) {
//            throw new TodoListMissingTextException("Todo text is required");
//        }
        return todoListRepository.addTodo(todo);
    }

    public List<Todo> getTodos() {
        return todoListRepository.getTodos();
    }
}
