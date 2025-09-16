package com.oocl.day12todolistbackend.repository;

import com.oocl.day12todolistbackend.dto.PostTodoReq;
import com.oocl.day12todolistbackend.dto.PutTodoReq;
import com.oocl.day12todolistbackend.entity.Todo;

import java.util.List;

public interface TodoListRepository {
    void resetData();

    Todo addTodo(PostTodoReq todo);

    List<Todo> getTodos();

    Todo updateTodo(PutTodoReq todo);

    Todo findTodoById(int id);
}
