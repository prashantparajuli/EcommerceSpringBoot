package com.codefest.product_service.service;

import com.codefest.product_service.DTO.ProductDTO;
import com.codefest.product_service.model.Category;
import com.codefest.product_service.model.Product;
import com.codefest.product_service.repository.CategoryRepository;
import com.codefest.product_service.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Method to fetch data from Fake Store API
    @Scheduled(fixedRate = 86400000) //it's in seconds
    public void syncProducts() {
        RestTemplate restTemplate = new RestTemplate();
        String FAKE_STORE_API = "https://fakestoreapi.com/products";
        ResponseEntity<Product[]> response = restTemplate.getForEntity(FAKE_STORE_API, Product[].class);

        List<Product> products = Arrays.asList(Objects.requireNonNull(response.getBody()));

        products.forEach(product -> {
//            System.out.println(product.getCategory().getName());
            if (product.getDescription() != null && product.getDescription().length() > 255) {
                product.setDescription(product.getDescription().substring(0, 255));
            }
            if (product.getCategory() != null) {
                Category category = categoryRepository.findByName(product.getCategory().getName())
                        .orElseGet(() -> categoryRepository.save(new Category(product.getCategory().getName())));
                product.setCategory(category);
            } else {
                System.out.println("Product with ID " + product.getId() + " has no category.");
            }
            productRepository.save(product);
        });
    }


    @Cacheable(value = "allProducts", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        Page<ProductDTO> productDTOPage = productPage.map(product -> new ProductDTO(product));

        return productDTOPage;
    }

    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductDTO(product);
    }

    @Cacheable(value = "productsByCategory", key = "#categoryName")
    public List<ProductDTO> getProductsByCategory(String categoryName) {
        System.out.println(categoryName);
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new RuntimeException("Category not found"));
        System.out.println(category);
        List<Product> products = productRepository.findByCategory(category);
        System.out.println("HELLO MFS : "+products);
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }
}
