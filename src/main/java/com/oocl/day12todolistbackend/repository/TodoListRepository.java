package com.oocl.day12todolistbackend.repository;

import com.oocl.day12todolistbackend.entity.Todo;

public interface TodoListRepository {
    public void resetData();

    public Todo addTodo(Todo todo);
}
