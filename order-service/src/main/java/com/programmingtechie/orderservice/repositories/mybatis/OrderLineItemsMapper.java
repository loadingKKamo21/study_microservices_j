package com.programmingtechie.orderservice.repositories.mybatis;

import com.programmingtechie.orderservice.models.OrderLineItems;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderLineItemsMapper {
    void save(OrderLineItems param);
    
    void saveAll(@Param("params") List<OrderLineItems> params);
    
    OrderLineItems findById(Long id);
    
    List<OrderLineItems> findAll();
    
    List<OrderLineItems> findAllByIds(@Param("ids") List<Long> ids);
    
    void deleteById(Long id);
    
    void deleteAllByIds(@Param("ids") List<Long> ids);
}
