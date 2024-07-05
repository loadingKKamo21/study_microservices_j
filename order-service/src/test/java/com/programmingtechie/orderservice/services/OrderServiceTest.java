package com.programmingtechie.orderservice.services;

import com.programmingtechie.orderservice.dtos.OrderLineItemsDto;
import com.programmingtechie.orderservice.dtos.OrderRequest;
import com.programmingtechie.orderservice.models.Order;
import com.programmingtechie.orderservice.models.OrderLineItems;
import com.programmingtechie.orderservice.repositories.mybatis.OrderLineItemsMapper;
import com.programmingtechie.orderservice.repositories.mybatis.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderServiceTest {
    
    @Autowired
    OrderService         orderService;
    @Autowired
    OrderMapper          orderMapper;
    @Autowired
    OrderLineItemsMapper orderLineItemsMapper;
    
    @Test
    @DisplayName("placeOrder")
    void placeOrder() {
        //Given
        OrderRequest orderRequest = getOrderRequest();
        
        //When
        orderService.placeOrder(orderRequest);
        
        //Then
        Order          findOrder          = orderMapper.findAll().get(0);
        OrderLineItems findOrderLineItems = orderLineItemsMapper.findAll().get(0);
        
        assertThat(findOrder).isNotNull();
        assertThat(findOrderLineItems.getOrderId()).isEqualTo(findOrder.getId());
        assertThat(findOrderLineItems.getSkuCode())
                .isEqualTo(orderRequest.getOrderLineItemsDtoList().get(0).getSkuCode());
        assertThat(findOrderLineItems.getPrice()).isEqualTo(orderRequest.getOrderLineItemsDtoList().get(0).getPrice());
        assertThat(findOrderLineItems.getQuantity())
                .isEqualTo(orderRequest.getOrderLineItemsDtoList().get(0).getQuantity());
    }
    
    private OrderRequest getOrderRequest() {
        List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>();
        orderLineItemsDtoList.add(new OrderLineItemsDto(null, "iphone_13", 1200.0, 1));
        return new OrderRequest(orderLineItemsDtoList);
    }
    
}