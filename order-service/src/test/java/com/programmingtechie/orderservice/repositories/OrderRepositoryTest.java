package com.programmingtechie.orderservice.repositories;

import com.programmingtechie.orderservice.models.Order;
import com.programmingtechie.orderservice.repositories.mybatis.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderRepositoryTest {
    
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper     orderMapper;
    
    @Test
    @DisplayName("save")
    void save() {
        //Given
        Order order = getOrder();
        
        //When
        Order savedOrder = orderRepository.save(order);
        
        //Then
        Order findOrder = orderMapper.findById(savedOrder.getId());
        
        assertThat(findOrder.getId()).isEqualTo(savedOrder.getId());
        assertThat(findOrder.getOrderNumber()).isEqualTo(savedOrder.getOrderNumber());
    }
    
    @Test
    @DisplayName("findById")
    void findById() {
        //Given
        Order order = getOrder();
        orderMapper.save(order);
        Long id = order.getId();
        
        //When
        Order findOrder = orderRepository.findById(id).get();
        
        //Then
        assertThat(findOrder.getId()).isEqualTo(order.getId());
        assertThat(findOrder.getOrderNumber()).isEqualTo(order.getOrderNumber());
    }
    
    @Test
    @DisplayName("findAll")
    void findAll() {
        //Given
        Random random = new Random();
        
        int size = random.nextInt(20) + 10;
        for (int i = 0; i < size; i++) {
            Order order = getOrder();
            orderMapper.save(order);
        }
        
        //When
        List<Order> findOrders = orderRepository.findAll().stream().sorted(comparing(Order::getId)).collect(toList());
        
        //Then
        List<Order> orders = orderMapper.findAll();
        for (int i = 0; i < size; i++) {
            Order order     = orders.get(i);
            Order findOrder = findOrders.get(i);
            
            assertThat(findOrder.getId()).isEqualTo(order.getId());
            assertThat(findOrder.getOrderNumber()).isEqualTo(order.getOrderNumber());
        }
    }
    
    @Test
    @DisplayName("deleteById")
    void deleteById() {
        //Given
        Order order = getOrder();
        orderMapper.save(order);
        Long id = order.getId();
        
        //When
        orderRepository.deleteById(id);
        
        //Then
        Order deletedOrder = orderMapper.findById(id);
        
        assertThat(deletedOrder).isNull();
    }
    
    private Order getOrder() {
        return Order.builder().orderNumber(UUID.randomUUID().toString()).build();
    }
    
}