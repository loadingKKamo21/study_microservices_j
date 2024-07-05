package com.programmingtechie.orderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDto {
    private Long   id;
    private String skuCode;
    private double price;
    private int    quantity;
}
