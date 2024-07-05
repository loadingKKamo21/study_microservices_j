package com.programmingtechie.inventoryservice.services;

import com.programmingtechie.inventoryservice.dtos.InventoryResponse;
import com.programmingtechie.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");
        return inventoryRepository.findAllBySkuCodeIn(skuCodes)
                                  .stream()
                                  .map(inventory -> new InventoryResponse(inventory.getSkuCode(),
                                                                          inventory.getQuantity() > 0))
                                  .collect(toList());
    }
    
}
