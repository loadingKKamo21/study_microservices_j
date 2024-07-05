package com.programmingtechie.orderservice.repositories;

import com.programmingtechie.orderservice.models.OrderLineItems;

import java.util.List;
import java.util.Optional;

public interface OrderLineItemsRepository {
    OrderLineItems save(OrderLineItems orderLineItems);
    
    List<OrderLineItems> saveAll(List<OrderLineItems> orderLineItemsList);
    
    Optional<OrderLineItems> findById(Long id);
    
    List<OrderLineItems> findAll();
    
    List<OrderLineItems> findAll(List<Long> ids);
    
    void deleteById(Long id);
    
    void deleteAllByIds(List<Long> ids);
}
