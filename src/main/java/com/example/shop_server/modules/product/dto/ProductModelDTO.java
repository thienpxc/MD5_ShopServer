package com.example.shop_server.modules.product.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModelDTO {
    private Integer id;
    @NotBlank(message = "Tên không được để trống")
    private String name;
    @Column(length = 10000)
    @NotBlank(message = "Mô tả không được để trống")
    private String description;
    @NotBlank(message = "Giá không được để trống")
    @Size(min = 0, message = "Giá không được nhỏ hơn 0")
    private Integer price;
    private Integer quantity;
    private Integer categoryId;

}
