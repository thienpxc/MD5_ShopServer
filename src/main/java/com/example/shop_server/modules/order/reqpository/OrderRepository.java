package com.example.shop_server.modules.order.reqpository;

import com.example.shop_server.modules.order.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel ,Integer> {
    @Query(value = "SELECT * FROM ordermodel WHERE ordermodel.status = :status AND ordermodel.user_id = :userId", nativeQuery = true)
    OrderModel findByUserIdAndStatus(@Param("userId") int userId, @Param("status") String status);

    @Query(value = "SELECT * FROM ordermodel WHERE ordermodel.user_id = :userId", nativeQuery = true)
    List<OrderModel> findByUserId(@Param("userId") int userId);


}


