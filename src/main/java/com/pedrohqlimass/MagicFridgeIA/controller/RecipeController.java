package com.pedrohqlimass.MagicFridgeIA.controller;

import com.pedrohqlimass.MagicFridgeIA.model.FoodItemModel;
import com.pedrohqlimass.MagicFridgeIA.service.FoodItemService;
import com.pedrohqlimass.MagicFridgeIA.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {

    private FoodItemService foodItemService;
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService, FoodItemService foodItemService) {
        this.recipeService = recipeService;
        this.foodItemService = foodItemService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe() {
        List<FoodItemModel> foodItemModels = foodItemService.listar();
        return recipeService.generateRecipe(foodItemModels)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
