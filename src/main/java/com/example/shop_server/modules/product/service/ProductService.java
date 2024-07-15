package com.example.shop_server.modules.product.service;

import com.example.shop_server.modules.category.CategoryModel;
import com.example.shop_server.modules.category.repository.CategoryRepository;
import com.example.shop_server.modules.product.ProductImg;
import com.example.shop_server.modules.product.ProductModel;
import com.example.shop_server.modules.product.dto.*;
import com.example.shop_server.modules.product.repository.ProductModelRepository;
import com.example.shop_server.modules.product.repository.ProductModelRepositoryImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductModelRepository productModelRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductModelRepositoryImg productImgRepository;

    //lấy sản phẩm theo id
    public ProductModel getProductById(Integer id) {
        return productModelRepository.findById(id).orElse(null);
    }

    //xoa hinh anh
    public void deleteImage(Integer id) {
        productImgRepository.deleteById(id);
    }
    //cập nhập sản phẩm
    private ProductModel updateProduct(UpdateDTO productDto) {
        System.out.println("hhhhhhhhhhhhhhhhhh"+productDto);
        // Find the existing product
        ProductModel existingProduct = productModelRepository.findById(productDto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update the existing product with the new values from the DTO
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());

        // Save the updated product
        return productModelRepository.save(existingProduct);
    }
    public ProductModel updateProductWithImages(UpdateWithDTO updateWithDto) {
        // Update the product information
        ProductModel updatedProduct = updateProduct(updateWithDto.getProduct());

        // Update the images
        for (ImageDTO imageDto : updateWithDto.getImages()) {
            createProductImages(updatedProduct, imageDto);
        }

        // Return the updated product
        return updatedProduct;
    }

    public Optional<ProductModel> findByIdProduct(Integer id){
        return productModelRepository.findById(id);
    }



    //hiện thi sản phẩm product admin
    public List<ProductModel> findAll() {
        return productModelRepository.findAll();
    }

    //hiện thị sản phẩm product user
    public List<ProductModel> findAllByStatusTrue() {
        return productModelRepository.findByStatusTrue();
    }
    //hiện thị sản phẩm nổi bật
    public List<ProductModel> findByisFeaturedTrue() {
        return productModelRepository.findByisFeaturedTrue();
    }
    //hiện thị sản phẩm theo category
    public List<ProductModel> findByCategoryIdAndStatusTrue(CategoryModel id) {
        return productModelRepository.findByCategoryIdAndStatusTrue(id);
    }
    //hiện thị sản phẩm theo id
    public ProductModel findById(Integer id) {
        return productModelRepository.findByIdAndStatusTrue(id).orElse(null);
    }

    //hiện thị sản phẩm mới nhất được thêm
    public List<ProductModel> findTop8ByOrderByIdDesc() {
        return productModelRepository.findTop8ByStatusTrueOrderByIdDesc();
    }

    //thêm sản phẩm
    private ProductModel createProduct(ProductModelDTO productDto) {
        ProductModel product = new ProductModel();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setCategoryId(categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));

        product.setStatus(true);

        return productModelRepository.save(product);
    }

    private ProductImg createProductImages(ProductModel product, ImageDTO imageDtos) {
        ProductImg variantImg = new ProductImg();
        variantImg.setProduct(product);
        variantImg.setImages(imageDtos.getImageUrl());
        return productImgRepository.save(variantImg);
    }

    public ProductModel addProduct(ProductWithDTO productDto) {
        if (productDto.getProduct() == null) {
            throw new IllegalArgumentException("ProductDto cannot be null");
        }
        ProductModel product = createProduct(productDto.getProduct());

        for (ImageDTO imageDto : productDto.getImages()) {
           ImageDTO imgDTO = new ImageDTO(null, imageDto.getImageUrl());
            createProductImages(product, imgDTO);
        }
        return product;
    }

    //chuyển đổi trạng thái sản phẩm
    public ProductModel updateProductStatus(Integer id) {
        ProductModel product = productModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStatus(!product.isStatus());
        return productModelRepository.save(product);
    }
    //chuyển đổi trạng thái isFeatured
    public ProductModel updateProductIsFeatured(Integer id) {
        ProductModel product = productModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setFeatured(!product.isFeatured());
        return productModelRepository.save(product);
    }

}
