package com.example.shop_server.modules.order.dto.req;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateQuantityDTO {
    private int orderDetailId;
    private int quantity;
}
