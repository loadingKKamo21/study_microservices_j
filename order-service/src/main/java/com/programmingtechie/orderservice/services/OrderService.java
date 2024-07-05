package com.programmingtechie.orderservice.services;

import com.programmingtechie.orderservice.dtos.InventoryResponse;
import com.programmingtechie.orderservice.dtos.OrderLineItemsDto;
import com.programmingtechie.orderservice.dtos.OrderRequest;
import com.programmingtechie.orderservice.models.Order;
import com.programmingtechie.orderservice.models.OrderLineItems;
import com.programmingtechie.orderservice.repositories.OrderLineItemsRepository;
import com.programmingtechie.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository          orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final WebClient.Builder        webClientBuilder;
    
    @Transactional
    public String placeOrder(OrderRequest orderRequest) {
        List<String> skuCodes = orderRequest.getOrderLineItemsDtoList().stream().map(OrderLineItemsDto::getSkuCode)
                                            .collect(toList());
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                                                                     .uri("http://inventory-service/api/inventories",
                                                                          uriBuilder -> uriBuilder
                                                                                  .queryParam("skuCodes", skuCodes)
                                                                                  .build())
                                                                     .retrieve()
                                                                     .bodyToMono(InventoryResponse[].class)
                                                                     .block();
        
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
        
        if (allProductsInStock) {
            Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).build();
            orderRepository.save(order);
            Long orderId = order.getId();
            
            List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                                                              .stream()
                                                              .map(orderLineItemsDto -> mapToDto(orderId,
                                                                                                 orderLineItemsDto))
                                                              .collect(toList());
            orderLineItemsRepository.saveAll(orderLineItems);
            
            return "Order Placed Successfully";
        } else
            throw new IllegalArgumentException("Product is not in stock, please try again later");
    }
    
    private OrderLineItems mapToDto(Long orderId, OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                             .orderId(orderId)
                             .skuCode(orderLineItemsDto.getSkuCode())
                             .price(orderLineItemsDto.getPrice())
                             .quantity(orderLineItemsDto.getQuantity())
                             .build();
    }
    
}
