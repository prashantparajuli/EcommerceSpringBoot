package main.java.com.codefest.ecommercespringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private int id;
    private String title;
    private Double price;
    private String description;
    private String image_url;
    @Getter
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category_id;
    private Double rating_rate;
    private Integer rating_count;

}
