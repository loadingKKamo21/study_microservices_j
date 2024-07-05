package com.programmingtechie.inventoryservice.services;

import com.programmingtechie.inventoryservice.dtos.InventoryResponse;
import com.programmingtechie.inventoryservice.models.Inventory;
import com.programmingtechie.inventoryservice.repositories.mybatis.InventoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class InventoryServiceTest {
    
    @Autowired
    InventoryService inventoryService;
    @Autowired
    InventoryMapper  inventoryMapper;
    
    @Test
    @DisplayName("isInStock")
    void isInStock() {
        //Given
        Inventory inventory = getInventory();
        inventoryMapper.save(inventory);
        List<String> skuCodes = Arrays.asList(inventory.getSkuCode());
        
        //When
        List<InventoryResponse> inventoryResponseList = inventoryService.isInStock(skuCodes);
        
        //Then
        InventoryResponse inventoryResponse = inventoryResponseList.get(0);
        
        assertThat(inventory.getSkuCode()).isEqualTo(inventoryResponse.getSkuCode());
        assertThat(inventory.getQuantity() > 0).isEqualTo(inventoryResponse.isInStock());
    }
    
    private Inventory getInventory() {
        return Inventory.builder().skuCode("iphone_13").quantity(100).build();
    }
    
}