package com.oocl.day12todolistbackend.repository;

import com.oocl.day12todolistbackend.dto.PostTodoReq;
import com.oocl.day12todolistbackend.dto.PutTodoReq;
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
    public Todo addTodo(PostTodoReq todo) {
        Todo newTodo = new Todo();
        newTodo.setText(todo.getText());
        newTodo.setDone(todo.isDone());
        return repository.save(newTodo);
    }

    @Override
    public List<Todo> getTodos() {
        return repository.findAll();
    }

    @Override
    public Todo updateTodo(PutTodoReq todo) {
        Todo updatedTodo = new Todo();
        updatedTodo.setId(todo.getId());
        updatedTodo.setText(todo.getText());
        updatedTodo.setDone(todo.isDone());
        return repository.save(updatedTodo);
    }

    @Override
    public Todo findTodoById(int id) {
        return null;
    }
}
