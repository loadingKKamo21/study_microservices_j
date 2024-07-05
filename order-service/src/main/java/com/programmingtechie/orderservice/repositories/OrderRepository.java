package com.programmingtechie.orderservice.repositories;

import com.programmingtechie.orderservice.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    
    Optional<Order> findById(Long id);
    
    List<Order> findAll();
    
    void deleteById(Long id);
}
