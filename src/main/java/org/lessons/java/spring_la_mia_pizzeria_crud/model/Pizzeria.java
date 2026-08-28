package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizzas")
public class Pizzeria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size (min = 5, max = 20, message = "Scegliere una lunghezza tra 5 e 20 caratteri")
    @Column (nullable = false)
    @NotBlank
    private String name;

    @NotBlank(message = "Inserire una descrizione")
    private String description;

    @NotBlank(message = "Inserire un url")
    private String img;

    @NotNull(message = "Inserire un prezzo")
    @Min(value = 1, message = "Il prezzo non può essere negativo o pari a zero")
    private Double price;

    // Getter e setter
    public Integer getId(){
        return this.id;
    }
    public void setId( Integer id ){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getImg(){
        return this.img;
    }
    public void setImg(String img){
        this.img = img;
    }

    public Integer getPrice(){
        return this.price;
    }
    public void setPrice(Integer price){
        this.price = price;
    }
}
