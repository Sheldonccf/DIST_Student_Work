package com.example.demo.Batch;




import com.example.demo.DAO.ListDAO;
import com.example.demo.model.Todo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Todo> {

    private ListDAO TodoRepository;

    @Autowired
    public DBWriter (ListDAO TodoRepository) {
        this.TodoRepository = TodoRepository;
    }

    @Override
    public void write(List<? extends Todo> Todos) throws Exception{
        System.out.println("Data Saved for Todos: " + Todos);
        TodoRepository.saveAll(Todos);
    }
}