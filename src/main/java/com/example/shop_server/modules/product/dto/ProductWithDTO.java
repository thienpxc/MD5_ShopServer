package com.example.shop_server.modules.product.dto;

import com.example.shop_server.modules.product.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithDTO {
    private ProductModelDTO product;
    private List<ImageDTO> images;
}
