package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    
    private final PizzaRepository repository;

    public PizzaController(PizzaRepository repository){
        this.repository = repository;
    }
/** 
    // @GetMapping
    public String index(Model model) {
        List<Pizzeria> pizzas = repository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }
    */
    @GetMapping
    public String index(@RequestParam(name = "name", required = false) String name, Model model) {
        List<Pizza> pizzas;
        if (name != null && !name.isBlank()){
            pizzas = repository.findByNameContainingIgnoringCase(name);
        } else {
            pizzas = repository.findAll();
        }
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> pizza = repository.findById(id);
        if (pizza.isEmpty()){
            return "redirect:/pizzas";
        }
        model.addAttribute("pizza", pizza.get());
        return "pizzas/pizzaDetail";
    }
    
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }
    
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizzeria, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "pizzas/create";
        }
        repository.save(formPizzeria);
        return "redirect:/pizzas";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizzeria, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "pizzas/edit";
        }
        repository.save(formPizzeria);
        return "redirect:/pizzas";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.deleteById(id);
        
        return "redirect:/pizzas";
    }
    

}
