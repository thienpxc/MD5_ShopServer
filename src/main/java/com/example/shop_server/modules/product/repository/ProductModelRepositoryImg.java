package com.example.shop_server.modules.product.repository;

import com.example.shop_server.modules.product.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductModelRepositoryImg extends JpaRepository<ProductImg, Integer> {
}
