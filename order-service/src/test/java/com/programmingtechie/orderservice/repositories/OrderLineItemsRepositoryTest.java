package com.programmingtechie.orderservice.repositories;

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
import java.util.Random;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderLineItemsRepositoryTest {
    
    @Autowired
    OrderLineItemsRepository orderLineItemsRepository;
    @Autowired
    OrderLineItemsMapper     orderLineItemsMapper;
    @Autowired
    OrderMapper              orderMapper;
    
    @Test
    @DisplayName("save")
    void save() {
        //Given
        OrderLineItems orderLineItems = getOrderLineItems(1).get(0);
        
        //When
        OrderLineItems savedOrderLineItems = orderLineItemsRepository.save(orderLineItems);
        
        //Then
        OrderLineItems findOrderLineItems = orderLineItemsMapper.findById(savedOrderLineItems.getId());
        
        assertThat(findOrderLineItems.getId()).isEqualTo(savedOrderLineItems.getId());
        assertThat(findOrderLineItems.getOrderId()).isEqualTo(savedOrderLineItems.getOrderId());
        assertThat(findOrderLineItems.getSkuCode()).isEqualTo(savedOrderLineItems.getSkuCode());
        assertThat(findOrderLineItems.getPrice()).isEqualTo(savedOrderLineItems.getPrice());
        assertThat(findOrderLineItems.getQuantity()).isEqualTo(savedOrderLineItems.getQuantity());
    }
    
    @Test
    @DisplayName("saveAll")
    void saveAll() {
        //Given
        Random random = new Random();
        int    size   = random.nextInt(20) + 10;
        
        List<OrderLineItems> orderLineItemsList = getOrderLineItems(size);
        
        //When
        List<OrderLineItems> savedOrderLineItemsList = orderLineItemsRepository.saveAll(orderLineItemsList);
        
        //Then
        List<OrderLineItems> findOrderLineItemsList = orderLineItemsMapper.findAll();
        
        for (int i = 0; i < size; i++) {
            OrderLineItems savedOrderLineItems = savedOrderLineItemsList.get(i);
            OrderLineItems findOrderLineItems  = findOrderLineItemsList.get(i);
            
            assertThat(savedOrderLineItems.getId()).isEqualTo(findOrderLineItems.getId());
            assertThat(savedOrderLineItems.getOrderId()).isEqualTo(findOrderLineItems.getOrderId());
            assertThat(savedOrderLineItems.getSkuCode()).isEqualTo(findOrderLineItems.getSkuCode());
            assertThat(savedOrderLineItems.getPrice()).isEqualTo(findOrderLineItems.getPrice());
            assertThat(savedOrderLineItems.getQuantity()).isEqualTo(findOrderLineItems.getQuantity());
        }
    }
    
    @Test
    @DisplayName("findById")
    void findById() {
        //Given
        OrderLineItems orderLineItems = getOrderLineItems(1).get(0);
        orderLineItemsMapper.save(orderLineItems);
        Long id = orderLineItems.getId();
        
        //When
        OrderLineItems findOrderLineItems = orderLineItemsRepository.findById(id).get();
        
        //Then
        assertThat(findOrderLineItems.getId()).isEqualTo(orderLineItems.getId());
        assertThat(findOrderLineItems.getOrderId()).isEqualTo(orderLineItems.getOrderId());
        assertThat(findOrderLineItems.getSkuCode()).isEqualTo(orderLineItems.getSkuCode());
        assertThat(findOrderLineItems.getPrice()).isEqualTo(orderLineItems.getPrice());
        assertThat(findOrderLineItems.getQuantity()).isEqualTo(orderLineItems.getQuantity());
    }
    
    @Test
    @DisplayName("findAll")
    void findAll() {
        //Given
        Random random = new Random();
        int    size   = random.nextInt(20) + 10;
        
        List<OrderLineItems> createdOrderLineItemsList = getOrderLineItems(size);
        orderLineItemsMapper.saveAll(createdOrderLineItemsList);
        
        //When
        List<OrderLineItems> findOrderLineItemsList = orderLineItemsRepository.findAll().stream()
                                                                              .sorted(comparing(OrderLineItems::getId))
                                                                              .collect(toList());
        
        //Then
        List<OrderLineItems> orderLineItemsList = orderLineItemsMapper.findAll();
        for (int i = 0; i < size; i++) {
            OrderLineItems orderLineItems     = orderLineItemsList.get(i);
            OrderLineItems findOrderLineItems = findOrderLineItemsList.get(i);
            
            assertThat(findOrderLineItems.getId()).isEqualTo(orderLineItems.getId());
            assertThat(findOrderLineItems.getOrderId()).isEqualTo(orderLineItems.getOrderId());
            assertThat(findOrderLineItems.getSkuCode()).isEqualTo(orderLineItems.getSkuCode());
            assertThat(findOrderLineItems.getPrice()).isEqualTo(orderLineItems.getPrice());
            assertThat(findOrderLineItems.getQuantity()).isEqualTo(orderLineItems.getQuantity());
        }
    }
    
    @Test
    @DisplayName("findAllByIds")
    void findAllByIds() {
        //Given
        Random random = new Random();
        int    size   = random.nextInt(20) + 10;
        
        List<OrderLineItems> createdOrderLineItemsList = getOrderLineItems(size);
        orderLineItemsMapper.saveAll(createdOrderLineItemsList);
        List<Long> ids = createdOrderLineItemsList.stream().map(OrderLineItems::getId).collect(toList());
        
        //When
        List<OrderLineItems> findOrderLineItemsList = orderLineItemsRepository.findAll(ids).stream()
                                                                              .sorted(comparing(OrderLineItems::getId))
                                                                              .collect(toList());
        
        //Then
        List<OrderLineItems> orderLineItemsList = orderLineItemsMapper.findAll().stream()
                                                                      .filter(orderLineItems -> ids.contains(
                                                                              orderLineItems.getId()))
                                                                      .collect(toList());
        for (int i = 0; i < size; i++) {
            OrderLineItems orderLineItems     = orderLineItemsList.get(i);
            OrderLineItems findOrderLineItems = findOrderLineItemsList.get(i);
            
            assertThat(findOrderLineItems.getId()).isEqualTo(orderLineItems.getId());
            assertThat(findOrderLineItems.getOrderId()).isEqualTo(orderLineItems.getOrderId());
            assertThat(findOrderLineItems.getSkuCode()).isEqualTo(orderLineItems.getSkuCode());
            assertThat(findOrderLineItems.getPrice()).isEqualTo(orderLineItems.getPrice());
            assertThat(findOrderLineItems.getQuantity()).isEqualTo(orderLineItems.getQuantity());
        }
    }
    
    @Test
    @DisplayName("deleteById")
    void deleteById() {
        //Given
        OrderLineItems orderLineItems = getOrderLineItems(1).get(0);
        orderLineItemsMapper.save(orderLineItems);
        Long id = orderLineItems.getId();
        
        //When
        orderLineItemsRepository.deleteById(id);
        
        //Then
        OrderLineItems deletedOrderLineItems = orderLineItemsMapper.findById(id);
        
        assertThat(deletedOrderLineItems).isNull();
    }
    
    @Test
    @DisplayName("deleteAllByIds")
    void deleteAllByIds() {
        //Given
        Random random = new Random();
        int    size   = random.nextInt(20) + 10;
        
        List<OrderLineItems> createdOrderLineItemsList = getOrderLineItems(size);
        orderLineItemsMapper.saveAll(createdOrderLineItemsList);
        List<Long> ids = createdOrderLineItemsList.stream().map(OrderLineItems::getId).collect(toList());
        
        //When
        orderLineItemsRepository.deleteAllByIds(ids);
        
        //Then
        List<OrderLineItems> deletedOrderLineItemsList = orderLineItemsMapper.findAll().stream()
                                                                             .filter(orderLineItems -> ids.contains(
                                                                                     orderLineItems.getId()))
                                                                             .collect(toList());
        
        assertThat(deletedOrderLineItemsList).isEmpty();
    }
    
    private List<OrderLineItems> getOrderLineItems(int size) {
        List<OrderLineItems> orderLineItemsList = new ArrayList<>();
        Random               random             = new Random();
        
        Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).build();
        orderMapper.save(order);
        for (int i = 1; i <= size; i++) {
            OrderLineItems orderLineItems = OrderLineItems.builder()
                                                          .orderId(order.getId())
                                                          .skuCode("Product_" + String.format(
                                                                  "%0" + String.valueOf(size).length() + "d", i))
                                                          .price(random.nextInt(100) + 10.0)
                                                          .quantity(random.nextInt(40) + 10)
                                                          .build();
            orderLineItemsList.add(orderLineItems);
        }
        return orderLineItemsList;
    }
    
}