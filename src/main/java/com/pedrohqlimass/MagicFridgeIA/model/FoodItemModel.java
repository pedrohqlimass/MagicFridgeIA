package com.pedrohqlimass.MagicFridgeIA.model;

import com.pedrohqlimass.MagicFridgeIA.model.enums.FoodItemCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "food_item")
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "food_item_category")
    private FoodItemCategory foodItemCategory;

    private Integer quantidade;

    private LocalDate validade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public FoodItemCategory getFoodItemCategory() {
        return foodItemCategory;
    }

    public void setFoodItemCategory(FoodItemCategory foodItemCategory) {
        this.foodItemCategory = foodItemCategory;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
}
