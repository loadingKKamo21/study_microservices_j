package com.programmingtechie.inventoryservice.repositories.mybatis;

import com.programmingtechie.inventoryservice.models.Inventory;
import com.programmingtechie.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {
    
    private final InventoryMapper inventoryMapper;
    
    @Override
    public Inventory save(Inventory inventory) {
        inventoryMapper.save(inventory);
        return inventory;
    }
    
    @Override
    public Optional<Inventory> findById(Long id) {
        return Optional.ofNullable(inventoryMapper.findById(id));
    }
    
    @Override
    public Optional<Inventory> findBySkuCode(String skuCode) {
        return Optional.ofNullable(inventoryMapper.findBySkuCode(skuCode));
    }
    
    @Override
    public List<Inventory> findAll() {
        return inventoryMapper.findAll();
    }
    
    @Override
    public void deleteById(Long id) {
        inventoryMapper.deleteById(id);
    }
    
    @Override
    public List<Inventory> findAllBySkuCodeIn(List<String> skuCodes) {
        return inventoryMapper.findAllBySkuCodeIn(skuCodes);
    }
    
}
