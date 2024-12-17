package com.ecommerce.sb_ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private Double price; // actual price 100
    private double discount; // 25%
    private double specialPrice; // after discount price 100 - {(25/100)*(100-> actual price)} = 75
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
