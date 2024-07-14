package com.example.shop_server.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWithDTO {
    private UpdateDTO product;
    private List<ImageDTO> images;
}
