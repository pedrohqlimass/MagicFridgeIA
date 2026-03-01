package com.pedrohqlimass.MagicFridgeIA.controller;

import com.pedrohqlimass.MagicFridgeIA.model.FoodItemModel;
import com.pedrohqlimass.MagicFridgeIA.service.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    //POST
    @PostMapping
    public ResponseEntity<FoodItemModel> criar(@RequestBody FoodItemModel foodItemModel) {
        FoodItemModel salvo = foodItemService.salvar(foodItemModel);
        return ResponseEntity.ok(salvo);
    }
    //GET
    @GetMapping
    public ResponseEntity<List<FoodItemModel>> listar() {

        List<FoodItemModel> foodItemModels = foodItemService.listar();

        if (foodItemModels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(foodItemModels);
    }

    //GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable Long id) {

        FoodItemModel foodItemModel = foodItemService.listarId(id);

        if (foodItemModel != null) {
            return ResponseEntity.ok(foodItemModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Este item não existe no banco de dados");
        }
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarItem(@PathVariable Long id, @RequestBody FoodItemModel model) {

        FoodItemModel itemAtualizado = foodItemService.atualizarItem(id, model);

        if (itemAtualizado != null) {
            return ResponseEntity.ok("Item atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Este item não existe no banco de dados");
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        foodItemService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}
