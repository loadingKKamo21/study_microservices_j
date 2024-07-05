package com.programmingtechie.orderservice.repositories.mybatis;

import com.programmingtechie.orderservice.models.Order;
import com.programmingtechie.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    
    private final OrderMapper orderMapper;
    
    @Override
    public Order save(Order order) {
        orderMapper.save(order);
        return order;
    }
    
    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orderMapper.findById(id));
    }
    
    @Override
    public List<Order> findAll() {
        return orderMapper.findAll();
    }
    
    @Override
    public void deleteById(Long id) {
        orderMapper.deleteById(id);
    }
    
}
