package com.example.shop_server.modules.order.dto.req;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddToCard {
    private int product;
    private int quantity;
    private double price;

    @Override
    public String toString() {
        return "AddToCartDTO{" +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
