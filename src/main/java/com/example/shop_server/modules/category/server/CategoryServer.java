package com.example.shop_server.modules.category.server;

import com.example.shop_server.modules.category.CategoryModel;
import com.example.shop_server.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServer {
    @Autowired
    private CategoryRepository categoryRepository;


    public List<CategoryModel> findAllCategories() {
        try{
            return categoryRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public CategoryModel addCategory(CategoryModel category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            return null;
        }
    }


}
