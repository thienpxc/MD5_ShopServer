package com.example.shop_server.modules.order;

import com.example.shop_server.modules.enums.OrderEnum;
import com.example.shop_server.modules.orderDetail.OrderDetailModel;
import com.example.shop_server.modules.user.UserModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    private double totalPrices = 0;

    @Enumerated(EnumType.STRING)
    private OrderEnum status = OrderEnum.SHOPPING;

    private String note;

    private String createDate = new Date().toString();

    private String updateDate;

    @Column(nullable = true)
    private Integer addressId;

    @OneToMany(mappedBy = "orders")
    @JsonManagedReference
    private List<OrderDetailModel> details = new ArrayList<>();


    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", user=" + user +
                ", totalPrices=" + totalPrices +
                ", status=" + status +
                ", note='" + note + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
