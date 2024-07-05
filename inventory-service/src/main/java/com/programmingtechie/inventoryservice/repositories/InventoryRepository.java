package com.programmingtechie.inventoryservice.repositories;

import com.programmingtechie.inventoryservice.models.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Inventory save(Inventory inventory);
    
    Optional<Inventory> findById(Long id);
    
    Optional<Inventory> findBySkuCode(String skuCode);
    
    List<Inventory> findAll();
    
    void deleteById(Long id);
    
    List<Inventory> findAllBySkuCodeIn(List<String> skuCodes);
}
