package com.example.shop_server.modules.product;

import com.example.shop_server.modules.category.CategoryModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<ProductImg> productImg;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryModel categoryId;
    private String createdDate = new Date().toString();
    private String updatedDate ;
    private boolean status = true;
    private boolean isFeatured = false;



    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +

                ", categoryId=" + categoryId +
                '}';
    }
}
