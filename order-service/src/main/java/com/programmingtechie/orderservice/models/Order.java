package com.programmingtechie.orderservice.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class Order {
    private Long       id;
    private String     orderNumber;
    private List<Long> orderLineItemsIdList = new ArrayList<>();
    
    @Builder
    public Order(Long id, String orderNumber) {
        this.id = id;
        this.orderNumber = orderNumber;
    }
}
