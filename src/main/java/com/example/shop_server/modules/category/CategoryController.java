package com.example.shop_server.modules.category;

import com.example.shop_server.modules.category.server.CategoryServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryServer categoryServer;

    @GetMapping("")
    public List<CategoryModel> findAllCategories() {
        return categoryServer.findAllCategories();
    }

//    @PostMapping("")
//    public CategoryModel createCategory(@RequestBody CategoryModel categoryModel) {
//
//    }

}
