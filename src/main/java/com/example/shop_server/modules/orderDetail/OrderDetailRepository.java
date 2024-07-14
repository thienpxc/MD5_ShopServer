package com.example.shop_server.modules.orderDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<OrderDetailModel, Integer> {



    @Query(value = "SELECT * FROM orderdetailmodel WHERE orderdetailmodel.order_id = :orderId and orderdetailmodel.product_Model_Id = :productVariantId", nativeQuery = true)
    OrderDetailModel findItem(@Param("orderId") int orderId, @Param("productVariantId") int productVariantId);
}
