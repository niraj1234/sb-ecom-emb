package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 3 , message = "Product name must contain at least 3 character")
    private String productName;

    @NotBlank
    @Size(min = 5 , message = "Product description min length is 5")
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn( name = "category_id" )
    private Category category;

    @ManyToOne
    @JoinColumn( name = "seller_id" )
    private User user;

    @OneToMany(mappedBy = "product" ,
            cascade = {CascadeType.PERSIST , CascadeType.MERGE},
            fetch = FetchType.EAGER)
    private List<CartItem> products = new ArrayList<>();

    
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount +
                ", specialPrice=" + specialPrice +
                '}';
    }
}
