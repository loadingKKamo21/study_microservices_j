package com.programmingtechie.inventoryservice.controllers;

import com.google.gson.Gson;
import com.programmingtechie.inventoryservice.dtos.InventoryResponse;
import com.programmingtechie.inventoryservice.services.InventoryService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
@ActiveProfiles("test")
class InventoryControllerTest {
    
    static final Gson GSON = new Gson();
    
    @MockBean
    InventoryService inventoryService;
    @Autowired
    MockMvc          mockMvc;
    List<InventoryResponse> inventoryResponseList;
    
    @BeforeEach
    void setup() {
        inventoryResponseList = Arrays.asList(
                new InventoryResponse("iphone_13", true),
                new InventoryResponse("iphone_13_red", false)
        );
        
        when(inventoryService.isInStock(anyList())).thenReturn(inventoryResponseList);
    }
    
    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("isInStock")
    void isInStock() {
        //Given
        
        //When
        ResultActions actions = mockMvc.perform(get("/api/inventories")
                                                        .param("skuCodes", "iphone_13", "iphone_13_red"));
        
        //Then
        actions.andExpect(status().isOk())
               .andExpect(content().json(GSON.toJson(inventoryResponseList)))
               .andDo(print());
        
        verify(inventoryService, times(1)).isInStock(anyList());
    }
    
}