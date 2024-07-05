package com.programmingtechie.inventoryservice.controllers;

import com.programmingtechie.inventoryservice.dtos.InventoryResponse;
import com.programmingtechie.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes){
        return inventoryService.isInStock(skuCodes);
    }
    
}
