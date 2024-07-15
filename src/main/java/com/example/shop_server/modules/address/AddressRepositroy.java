package com.example.shop_server.modules.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepositroy  extends JpaRepository<AddressModel, Integer> {
    @Query(value = "SELECT * FROM address WHERE address.userId = :userId", nativeQuery = true)
    List<AddressModel> findByUserId(@Param("userId") Integer userId);
}
