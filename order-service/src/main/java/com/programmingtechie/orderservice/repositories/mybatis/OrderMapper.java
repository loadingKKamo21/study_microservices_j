package com.programmingtechie.orderservice.repositories.mybatis;

import com.programmingtechie.orderservice.models.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void save(Order param);
    
    Order findById(Long id);
    
    List<Order> findAll();
    
    void deleteById(Long id);
}
