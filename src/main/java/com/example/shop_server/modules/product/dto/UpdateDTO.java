package com.example.shop_server.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.PrimitiveIterator;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;


}
