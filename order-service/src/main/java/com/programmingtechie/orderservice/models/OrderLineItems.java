package com.programmingtechie.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class OrderLineItems {
    private Long   id;
    private Long   orderId;
    private String skuCode;
    private double price;
    private int    quantity;
}
