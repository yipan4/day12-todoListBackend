package com.oocl.day12todolistbackend.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oocl.day12todolistbackend.entity.Todo;

@Repository
public interface TodoListJpaRepository extends JpaRepository<Todo, Integer> {

}
