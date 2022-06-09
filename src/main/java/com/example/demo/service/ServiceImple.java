package com.example.demo.service;

import com.example.demo.DAO.ListDAO;
import com.example.demo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@org.springframework.stereotype.Service
public class ServiceImple implements Service{


    private ListDAO listDAO;

    @Autowired
    public ServiceImple(ListDAO listDAO) {
        this.listDAO = listDAO;
    }

    @Override
    @Transactional
    public List<Todo> getAllList() {
        Iterator<Todo> iterator = listDAO.findAll().iterator();
        List<Todo> actualList = new ArrayList<Todo>();
        while (iterator.hasNext()) {
            actualList.add(iterator.next()); }

        return actualList;
    }

    @Override
    @Transactional
    public Todo findById(int id) {
        return listDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Todo todolist) {
        listDAO.save(todolist);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        listDAO.deleteById(id);
    }

    @Override
    @Transactional
    public boolean existById(int id) {
        return listDAO.existsById(id);
    }
}
