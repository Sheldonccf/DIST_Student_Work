package com.example.demo.DAO;

import com.example.demo.model.Todo;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public interface ListDAO extends CrudRepository<Todo, Integer> {

}
