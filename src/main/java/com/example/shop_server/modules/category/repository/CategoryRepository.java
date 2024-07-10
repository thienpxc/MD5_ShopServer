package com.example.shop_server.modules.category.repository;

import com.example.shop_server.modules.category.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {
}
