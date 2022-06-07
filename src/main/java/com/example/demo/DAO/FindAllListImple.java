//package com.example.demo.DAO;

import com.example.demo.model.Todo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Repository
//public class FindAllListImple implements FindAllList {

  // @PersistenceContext
   //private EntityManager entityManager;

  // @Transactional
  // public List<Todo> getAllList() {
        //get the current session
    //  Session currentSession = entityManager.unwrap(Session.class);
        //create a query
     //  Query<Todo> theQuery = currentSession.createNativeQuery("SELECT * FROM toDoList t ", Todo.class);

        //execute query and get result
    //  List<Todo> Todos = theQuery.getResultList();

        //return the results
     //  return Todos;
 //}

   // @Override
  //  public Todo findById(int id) {
 //       Session currentSession = entityManager.unwrap(Session.class);
//
//        Query<Todo> theQuery = currentSession.createNativeQuery("SELECT * FROM toDoList t WHERE t.id=id", Todo.class);
//
 //       Todo toDo = theQuery.getResultList().get(0);
//
 //       return toDo;
 //   }
//
//    @Override
//    public void save(Todo todolist) {
//        Session currentSession = entityManager.unwrap(Session.class);
//
//        currentSession.saveOrUpdate(todolist);
//
//
//    }
//
//    @Override
//    public void deleteById(int theId) {
//        Session currentSession = entityManager.unwrap(Session.class);
//
//        Todo temp =  currentSession.find(Todo.class, theId);
//        if (temp != null) {
//            currentSession.remove(temp);
//        }
//    }
//}
