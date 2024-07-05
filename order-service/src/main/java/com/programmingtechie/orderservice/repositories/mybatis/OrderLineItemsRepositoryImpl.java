package com.programmingtechie.orderservice.repositories.mybatis;

import com.programmingtechie.orderservice.models.OrderLineItems;
import com.programmingtechie.orderservice.repositories.OrderLineItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderLineItemsRepositoryImpl implements OrderLineItemsRepository {
    
    private final OrderLineItemsMapper orderLineItemsMapper;
    
    @Override
    public OrderLineItems save(OrderLineItems orderLineItems) {
        orderLineItemsMapper.save(orderLineItems);
        return orderLineItems;
    }
    
    @Override
    public List<OrderLineItems> saveAll(List<OrderLineItems> orderLineItemsList) {
        orderLineItemsMapper.saveAll(orderLineItemsList);
//        for (OrderLineItems orderLineItems : orderLineItemsList)
//            orderLineItemsMapper.save(orderLineItems);
        return orderLineItemsList;
    }
    
    @Override
    public Optional<OrderLineItems> findById(Long id) {
        return Optional.ofNullable(orderLineItemsMapper.findById(id));
    }
    
    @Override
    public List<OrderLineItems> findAll() {
        return orderLineItemsMapper.findAll();
    }
    
    @Override
    public List<OrderLineItems> findAll(List<Long> ids) {
        return orderLineItemsMapper.findAllByIds(ids);
    }
    
    @Override
    public void deleteById(Long id) {
        orderLineItemsMapper.deleteById(id);
    }
    
    @Override
    public void deleteAllByIds(List<Long> ids) {
        orderLineItemsMapper.deleteAllByIds(ids);
    }
    
}
