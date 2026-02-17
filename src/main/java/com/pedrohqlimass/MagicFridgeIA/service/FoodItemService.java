package com.pedrohqlimass.MagicFridgeIA.service;

import com.pedrohqlimass.MagicFridgeIA.model.FoodItemModel;
import com.pedrohqlimass.MagicFridgeIA.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    //POST
    public FoodItemModel salvar(FoodItemModel foodItemModel) {
        return repository.save(foodItemModel);
    }

    //GET
    public List<FoodItemModel> listar() {
        return repository.findAll();
    }

    //GET POR ID
    public FoodItemModel listarId(Long id) {
        return repository.findById(id)
                .orElse(null);
    }

    //PUT
    public FoodItemModel atualizarItem(Long id, FoodItemModel model) {
        return repository.findById(id)
                .map(item -> {
                    item.setNome(model.getNome());
                    item.setFoodItemCategory(model.getFoodItemCategory());
                    item.setQuantidade(model.getQuantidade());
                    item.setValidade(model.getValidade());
                    return repository.save(item);
                })
                .orElse(null);
    }

    //DELETE
    public void deletarItem(Long id) {
        repository.deleteById(id);
    }
}
