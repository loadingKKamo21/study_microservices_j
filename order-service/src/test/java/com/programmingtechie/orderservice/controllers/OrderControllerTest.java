package com.programmingtechie.orderservice.controllers;

import com.google.gson.Gson;
import com.programmingtechie.orderservice.dtos.OrderLineItemsDto;
import com.programmingtechie.orderservice.dtos.OrderRequest;
import com.programmingtechie.orderservice.services.OrderService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {
    
    static final Gson GSON = new Gson();
    
    @MockBean
    OrderService orderService;
    @Autowired
    MockMvc      mockMvc;
    
    @BeforeEach
    void setup() {
        doNothing().when(orderService).placeOrder(any(OrderRequest.class));
    }
    
    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("placeOrder")
    void placeOrder() {
        //Given
        
        //When
        ResultActions actions = mockMvc.perform(post("/api/orders")
                                                        .contentType(APPLICATION_JSON_VALUE)
                                                        .content(GSON.toJson(getOrderRequest())));
        
        //Then
        actions.andExpect(status().isOk())
               .andExpect(content().string("Order Placed Successfully"))
               .andDo(print());
        
        verify(orderService, times(1)).placeOrder(any(OrderRequest.class));
    }
    
    private OrderRequest getOrderRequest() {
        List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>();
        orderLineItemsDtoList.add(new OrderLineItemsDto(null, "iphone_13", 1200.0, 1));
        return new OrderRequest(orderLineItemsDtoList);
    }
}