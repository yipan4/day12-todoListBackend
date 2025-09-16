package com.oocl.day12todolistbackend.repository;

import com.oocl.day12todolistbackend.entity.Todo;

import java.util.List;

public interface TodoListRepository {
    void resetData();

    Todo addTodo(Todo todo);

    List<Todo> getTodos();
}
