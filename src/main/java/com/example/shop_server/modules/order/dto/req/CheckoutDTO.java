package com.example.shop_server.modules.order.dto.req;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CheckoutDTO {
    Integer addressId;
    Integer id;
    Double total;


}
