package org.lessons.java.spring_la_mia_pizzeria_crud.repository;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizzeria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzeriaRepository extends JpaRepository<Pizzeria, Integer>{
    public List<Pizzeria> findByNameContainingIgnoringCase(String name);
}
