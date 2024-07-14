package com.example.shop_server.modules.category.repository;

import com.example.shop_server.modules.category.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {
    List<CategoryModel> findByNameIsContainingIgnoreCase(String name);

    List<CategoryModel> findByStatusTrue();

    //hiẹn thi  sản phẩm theo category

}
