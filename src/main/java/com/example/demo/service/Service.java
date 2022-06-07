package com.example.demo.service;

import com.example.demo.model.Todo;

import java.util.List;

public interface Service {

    public List<Todo> getAllList();

    public Todo findById(int id);

    public void save(Todo todolist);

    public void deleteById(int id);
}
