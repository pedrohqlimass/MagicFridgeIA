package com.pedrohqlimass.MagicFridgeIA.repository;

import com.pedrohqlimass.MagicFridgeIA.model.FoodItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItemModel, Long> {
}
