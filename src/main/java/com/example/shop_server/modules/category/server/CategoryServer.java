package com.example.shop_server.modules.category.server;

import com.example.shop_server.modules.category.CategoryModel;
import com.example.shop_server.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteCategory(Integer id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CategoryModel findById(Integer id) {
        try {
            Optional<CategoryModel> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                return category.get();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Page<CategoryModel> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public List<CategoryModel> findByNameIsContainingIgnoreCase(String name) {
        return categoryRepository.findByNameIsContainingIgnoreCase(name);
    }

    //hiện thị catetegory với trạng thái là true
    public List<CategoryModel> findByStatusTrue() {
        try{
            return categoryRepository.findByStatusTrue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public CategoryModel getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }









}
