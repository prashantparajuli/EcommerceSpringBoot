package com.codefest.product_service.DTO;

import com.codefest.product_service.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String imageUrl;
    private String categoryName;
    private double ratingRate;
    private int ratingCount;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imageUrl = product.getImageUrl();

        if (product.getCategory() != null) {
            this.categoryName = product.getCategory().getName();
        }
        if (product.getRating() != null) {
            this.ratingRate = product.getRating().getRating_rate();
            this.ratingCount = product.getRating().getRating_count();
        }
    }
}
