package com.oocl.day12todolistbackend.repository;

import com.oocl.day12todolistbackend.entity.Todo;
import com.oocl.day12todolistbackend.repository.dao.TodoListJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoListRepositoryImpl implements TodoListRepository{
    @Autowired
    private TodoListJpaRepository repository;

    @Override
    public void resetData() {
        repository.deleteAll();
    }

    @Override
    public Todo addTodo(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public List<Todo> getTodos() {
        return repository.findAll();
    }
}
