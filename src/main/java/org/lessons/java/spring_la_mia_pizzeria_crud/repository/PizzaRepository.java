package org.lessons.java.spring_la_mia_pizzeria_crud.repository;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

// Forniamo metodi per eseguire le CRUD su Pizza e diamo il tipo di dato dell'id nella entry, quindi Integer
public interface PizzaRepository extends JpaRepository<Pizza, Integer>{
    public List<Pizza> findByNameContainingIgnoringCase(String name); // query custom
}
