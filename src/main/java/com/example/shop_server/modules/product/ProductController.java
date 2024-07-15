package com.example.shop_server.modules.product;

import com.example.shop_server.modules.category.CategoryModel;
import com.example.shop_server.modules.category.server.CategoryServer;
import com.example.shop_server.modules.product.dto.ProductWithDTO;
import com.example.shop_server.modules.product.dto.UpdateDTO;
import com.example.shop_server.modules.product.dto.UpdateWithDTO;
import com.example.shop_server.modules.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    //hiện thi sản phẩm product admin
    @Autowired
    private CategoryServer categoryService;
    @GetMapping("/admin/products")
    public List<ProductModel> findAll() {
        return productService.findAll();
    }

//    them san pham
    @PostMapping("/admin/products")
    public ResponseEntity<?> add(@Valid @RequestBody ProductWithDTO product) {
        try{
            ProductModel productModel = productService.addProduct(product);
            return ResponseEntity.ok(productModel);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Thêm sản phẩm thất bại");
        }

    }
    //lấy sản phẩm theo id
    @GetMapping("/admin/products/{id}")
    public ProductModel getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    //xoa hinh anh
    @DeleteMapping("/admin/products/{id}")
    public void deleteImage(@PathVariable Integer id) {
        productService.deleteImage(id);
    }
    //cập nhập sản phẩm
    @PutMapping("/admin/products")
    public ProductModel updateProduct(@RequestBody UpdateWithDTO updateWithDto) {
        return productService.updateProductWithImages(updateWithDto);
    }

    @GetMapping("")
    public List<ProductModel> findByisFeaturedTrue() {
        return productService.findByisFeaturedTrue();
    }

    @GetMapping("/category/{id}")
    public List<ProductModel> findByCategoryId(@PathVariable Integer id) {
        CategoryModel category = categoryService.getCategoryById(id); // assuming you have a method to get CategoryModel by id
        return productService.findByCategoryIdAndStatusTrue(category);
    }

    ////hiện thị sản phẩm mới nhất được thêm
    @GetMapping("/new-products")
    public List<ProductModel> findTop8ByOrderByIdDesc() {
        return productService.findTop8ByOrderByIdDesc();
    }

    //chuyển đổi trạng thái sản phẩm
    @PutMapping("/admin/products/{id}/status")
    public ProductModel updateProductStatus(@PathVariable Integer id) {
        return productService.updateProductStatus(id);
    }

    //chuy doi trạng thai isFeatured
    @PutMapping("/admin/products/{id}/isFeatured")
    public ProductModel updateProductIsFeatured(@PathVariable Integer id) {
        return productService.updateProductIsFeatured(id);
    }




    @GetMapping("/search")
    public List<ProductModel> searchProductByName(@RequestParam String name) {
        return productService.searchProductByName(name);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getProductByPagination(@RequestParam int offset, @RequestParam int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<ProductModel> pageProducts = productService.findAll(pageable);
        return ResponseEntity.ok(pageProducts.getContent());
    }

}
