package com.example.shop_server.modules.orderDetail;

import com.example.shop_server.modules.order.OrderModel;
import com.example.shop_server.modules.product.ProductModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDetailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderModel orders;


    @ManyToOne
    @JoinColumn(name = "product_Model_Id")
    private ProductModel productmodel;
    private double price;
    private int quantity;


    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orders=" + orders +

                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
