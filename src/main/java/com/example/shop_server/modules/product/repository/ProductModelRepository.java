package com.example.shop_server.modules.product.repository;

import com.example.shop_server.modules.category.CategoryModel;
import com.example.shop_server.modules.product.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductModelRepository extends JpaRepository<ProductModel, Integer> {
    List<ProductModel> findByStatusTrue();
    List<ProductModel> findByisFeaturedTrue();
    List<ProductModel> findByCategoryIdAndStatusTrue(CategoryModel category);

    Optional<ProductModel> findByIdAndStatusTrue(Integer id);

    List<ProductModel> findTop8ByStatusTrueOrderByIdDesc();

    Optional<ProductModel> findById(Integer id);

    //hiẹn thị sản phẩm theo category


}
