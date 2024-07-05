package com.programmingtechie.orderservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private String  skuCode;
    @JsonProperty("isInStock")
    private boolean isInStock;
}
