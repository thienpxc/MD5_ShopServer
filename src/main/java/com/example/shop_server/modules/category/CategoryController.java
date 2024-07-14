package com.example.shop_server.modules.category;

import com.example.shop_server.modules.category.dto.rep.AddCategoryDTO;
import com.example.shop_server.modules.category.repository.CategoryRepository;
import com.example.shop_server.modules.category.server.CategoryServer;
import com.example.shop_server.modules.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryServer categoryServer;



    @GetMapping("")
    public List<CategoryModel> findAllCategory() {
        return categoryServer.findAllCategories();
    }

    @PostMapping("")
    public ResponseEntity<CategoryModel> addCategory(@RequestBody AddCategoryDTO data, @RequestAttribute("data") UserModel user) {

        if(!user.getPermission().contains("category.c")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        CategoryModel category = new CategoryModel();
        category.setName(data.getName());
        category.setImage(data.getImage());
        return new ResponseEntity<>(categoryServer.addCategory(category), HttpStatus.OK);

    }

    @PostMapping("/update")
    public CategoryModel updateCategory(@RequestBody CategoryModel category) {
        return categoryServer.addCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryServer.deleteCategory(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getUserByPagination(@RequestParam int offset, @RequestParam int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<CategoryModel> pageUsers = categoryServer.findAll(pageable);
        return ResponseEntity.ok(pageUsers.getContent());
    }

    @GetMapping("/search")
public ResponseEntity<?> searchCategoryByName(@RequestParam String name) {
    List<CategoryModel> categories = categoryServer.findByNameIsContainingIgnoreCase(name);
    return ResponseEntity.ok(categories);
}

  @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        CategoryModel category = categoryServer.findById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public List<CategoryModel> findAllCategoryTrue() {
        return categoryServer.findByStatusTrue();
    }

    //hiện thi sản phẩm theo category

}
