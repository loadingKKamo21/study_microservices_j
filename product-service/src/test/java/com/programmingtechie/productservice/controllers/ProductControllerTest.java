package com.programmingtechie.productservice.controllers;

import com.google.gson.Gson;
import com.programmingtechie.productservice.dtos.ProductRequest;
import com.programmingtechie.productservice.dtos.ProductResponse;
import com.programmingtechie.productservice.services.ProductService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerTest {
    
    static final Gson GSON = new Gson();
    
    @MockBean
    ProductService productService;
    @Autowired
    MockMvc        mockMvc;
    List<ProductResponse> productResponses;
    
    @BeforeEach
    void setup() {
        Random random = new Random();
        productResponses = new ArrayList<>();
        
        int size = 10;
        for (int i = 1; i <= size; i++) {
            ProductResponse productResponse = new ProductResponse((long) i,
                                                                  "Product" + String.format(
                                                                          "%0" + String.valueOf(size).length() + "d",
                                                                          i),
                                                                  "Product" + String.format(
                                                                          "%0" + String.valueOf(size).length() + "d",
                                                                          i),
                                                                  random.nextInt(100) + 10.0);
            productResponses.add(productResponse);
        }
        
        doNothing().when(productService).createProduct(any(ProductRequest.class));
        when(productService.getAllProducts()).thenReturn(productResponses);
    }
    
    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("createProduct")
    void createProduct() {
        //Given
        
        //When
        ResultActions actions = mockMvc.perform(post("/api/products")
                                                        .contentType(APPLICATION_JSON_VALUE)
                                                        .content(GSON.toJson(
                                                                new ProductRequest("iPhone 13", "iPhone 13", 1200.0))));
        
        //Then
        actions.andExpect(status().isCreated())
               .andDo(print());
        
        verify(productService, times(1)).createProduct(any(ProductRequest.class));
    }
    
    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("getAllProducts")
    void getAllProducts() {
        //Given
        
        //When
        ResultActions actions = mockMvc.perform(get("/api/products"));
        
        //Then
        actions.andExpect(status().isOk())
               .andExpect(content().json(GSON.toJson(productResponses)))
               .andDo(print());
        
        verify(productService, times(1)).getAllProducts();
    }
    
}