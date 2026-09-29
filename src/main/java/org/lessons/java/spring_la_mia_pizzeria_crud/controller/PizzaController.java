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
@RequestMapping("/pizzas") // il controller gestisce le chiamate di base da questo url
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
    @GetMapping // Chiamata di tipo GET
    public String index(@RequestParam(name = "name", required = false) String name, Model model) {
        List<Pizza> pizzas;
        if (name != null && !name.isBlank()){
            pizzas = repository.findByNameContainingIgnoringCase(name);
        } else {
            pizzas = repository.findAll();
        }
        model.addAttribute("pizzas", pizzas); // Aggiungo a "pizzas" il risultato caricato dal DB
        return "pizzas/index"; // restituisco questo Model a index dentro pizzas
    }

    @GetMapping("/{id}") // @PathVariable per passare nell'url l'id dell'elemento da recuperare 
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> pizza = repository.findById(id); // carichiamo l'elemento dal DB
        if (pizza.isEmpty()){
            return "redirect:/pizzas";
        }
        model.addAttribute("pizza", pizza.get());
        return "pizzas/pizzaDetail";
    }
    
    @GetMapping("/create") // restituisce una view con form
    // imposta nel model un attributo "pizza" con cui popolare il form
    public String create(Model model) {
        // le colonne in th sono fields
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create"; 
    }
    
    @PostMapping("/create") // al momento del submit
    public String store(
        @Valid @ModelAttribute("pizza") Pizza formPizza, // Valid fa il check della validazione in Entity
        BindingResult bindingResult, // metodo passato come parametro
        Model model) {
        
            if (bindingResult.hasErrors()){ // cerco se ci sono errori
            return "pizzas/create"; // se si ritorno il form dove segnalo gli errori da correggere
        }
        repository.save(formPizza); // se non ci sono errori, salvo
        return "redirect:/pizzas"; // redirect per evitare doppia imissione di dati
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get()); // passiamo il record che vogliamo editare
        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
        @Valid @ModelAttribute("pizza") Pizza formPizza,  // Otteniamo i dati passati tramite form
        BindingResult bindingResult, 
        Model model) {

        if (bindingResult.hasErrors()){
            return "pizzas/edit";
        }
        repository.save(formPizza); // salvo
        return "redirect:/pizzas";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.deleteById(id);
        
        return "redirect:/pizzas";
    }
    

}
