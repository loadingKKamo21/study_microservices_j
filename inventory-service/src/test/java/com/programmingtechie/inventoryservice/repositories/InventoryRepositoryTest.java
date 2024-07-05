package com.programmingtechie.inventoryservice.repositories;

import com.programmingtechie.inventoryservice.models.Inventory;
import com.programmingtechie.inventoryservice.repositories.mybatis.InventoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class InventoryRepositoryTest {
    
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    InventoryMapper     inventoryMapper;
    
    @Test
    @DisplayName("save")
    void save() {
        //Given
        Inventory inventory = getInventory();
        
        //When
        Inventory savedInventory = inventoryRepository.save(inventory);
        
        //Then
        Inventory findInventory = inventoryMapper.findById(savedInventory.getId());
        
        assertThat(findInventory.getId()).isEqualTo(savedInventory.getId());
        assertThat(findInventory.getSkuCode()).isEqualTo(savedInventory.getSkuCode());
        assertThat(findInventory.getQuantity()).isEqualTo(savedInventory.getQuantity());
    }
    
    @Test
    @DisplayName("findById")
    void findById() {
        //Given
        Inventory inventory = getInventory();
        inventoryMapper.save(inventory);
        Long id = inventory.getId();
        
        //When
        Inventory findInventory = inventoryRepository.findById(id).get();
        
        //Then
        assertThat(findInventory.getId()).isEqualTo(inventory.getId());
        assertThat(findInventory.getSkuCode()).isEqualTo(inventory.getSkuCode());
        assertThat(findInventory.getQuantity()).isEqualTo(inventory.getQuantity());
    }
    
    @Test
    @DisplayName("findBySkuCode")
    void findBySkuCode() {
        //Given
        Inventory inventory = getInventory();
        inventoryMapper.save(inventory);
        String skuCode = inventory.getSkuCode();
        
        //When
        Inventory findInventory = inventoryRepository.findBySkuCode(skuCode).get();
        
        //Then
        assertThat(findInventory.getId()).isEqualTo(inventory.getId());
        assertThat(findInventory.getSkuCode()).isEqualTo(inventory.getSkuCode());
        assertThat(findInventory.getQuantity()).isEqualTo(inventory.getQuantity());
    }
    
    @Test
    @DisplayName("findAll")
    void findAll() {
        //Given
        Random random = new Random();
        
        int size = random.nextInt(20) + 10;
        for (int i = 0; i < size; i++) {
            Inventory inventory = getInventory();
            inventoryMapper.save(inventory);
        }
        
        //When
        List<Inventory> findInventories = inventoryRepository.findAll().stream().sorted(comparing(Inventory::getId))
                                                             .collect(toList());
        
        //Then
        List<Inventory> inventories = inventoryMapper.findAll();
        for (int i = 0; i < size; i++) {
            Inventory inventory     = inventories.get(i);
            Inventory findInventory = findInventories.get(i);
            
            assertThat(findInventory.getId()).isEqualTo(inventory.getId());
            assertThat(findInventory.getSkuCode()).isEqualTo(inventory.getSkuCode());
            assertThat(findInventory.getQuantity()).isEqualTo(inventory.getQuantity());
        }
    }
    
    @Test
    @DisplayName("deleteById")
    void deleteById() {
        //Given
        Inventory inventory = getInventory();
        inventoryMapper.save(inventory);
        Long id = inventory.getId();
        
        //When
        inventoryRepository.deleteById(id);
        
        //Then
        Inventory deletedInventory = inventoryMapper.findById(id);
        
        assertThat(deletedInventory).isNull();
    }
    
    private Inventory getInventory() {
        return Inventory.builder().skuCode("iphone_13").quantity(100).build();
    }
    
}