package com.codefest.product_service.repository;

import com.codefest.product_service.model.Category;
import com.codefest.product_service.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    Page<Product> findAll(Pageable pageable);
    List<Product> findByCategory_Name(String category_name);

    Optional<Product> findByTitle(String title);

    List<Product> findByCategory(Category category);
}
