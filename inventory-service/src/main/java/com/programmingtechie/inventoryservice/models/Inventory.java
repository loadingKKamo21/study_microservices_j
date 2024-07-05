package com.programmingtechie.inventoryservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class Inventory {
    private Long   id;
    private String skuCode;
    private int    quantity;
}
