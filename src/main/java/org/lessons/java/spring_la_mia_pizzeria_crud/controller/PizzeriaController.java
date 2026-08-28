package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizzeria;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzeriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/pizzas")
public class PizzeriaController {
    
    private final PizzeriaRepository repository;

    public PizzeriaController(PizzeriaRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public String index(Model model) {
        List<Pizzeria> pizzas = repository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas.index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizzeria pizza = repository.findById(id).get();
        model.addAttribute("pizza", pizza);
        return "/pizzas/pizzaDetail";
    }

    @GetMapping("/findByName")
    public String findByName(@RequestParam(name = "name") String name, Model model) {
        List<Pizzeria> pizzas = repository.findByNameContaining(name);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }
    
}
