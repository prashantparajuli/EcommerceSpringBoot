package com.codefest.product_service;

import com.codefest.product_service.model.Product;
import com.codefest.product_service.model.Category;
import com.codefest.product_service.model.Rating;
import com.codefest.product_service.DTO.ProductDTO;
import com.codefest.product_service.repository.ProductRepository;
import com.codefest.product_service.repository.CategoryRepository;
import com.codefest.product_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;
    private Rating rating;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Category
        category = new Category();
        category.setName("electronics");

        // Mock Rating
        rating = new Rating(4.5, 200);

        // Mock Product
        product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");
        product.setImageUrl("http://example.com/image.jpg");
        product.setCategory(category);
        product.setRating(rating);
    }

    @Test
    void testGetProductById() {
        // Mock the repository method
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Call the service method
        ProductDTO productDTO = productService.getProductById(1L);

        // Assertions
        assertNotNull(productDTO);
        assertEquals("Test Product", productDTO.getTitle());
        assertEquals(100.0, productDTO.getPrice());
        assertEquals("electronics", productDTO.getCategoryName());

        // Verify that the repository was called
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        // Mock the repository method to return empty
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and assert exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        assertEquals("Product not found", exception.getMessage());

        // Verify that the repository was called
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductsByCategory() {
        // Mock the repository and category methods
        when(categoryRepository.findByName("electronics")).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(category)).thenReturn(Arrays.asList(product));

        // Call the service method
        List<ProductDTO> productDTOList = productService.getProductsByCategory("electronics");

        // Assertions
        assertNotNull(productDTOList);
        assertEquals(1, productDTOList.size());
        assertEquals("Test Product", productDTOList.get(0).getTitle());

        // Verify interactions
        verify(categoryRepository, times(1)).findByName("electronics");
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void testGetProductsByCategoryNotFound() {
        // Mock the repository to return empty category
        when(categoryRepository.findByName("electronics")).thenReturn(Optional.empty());

        // Call the service method and assert exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProductsByCategory("electronics"));
        assertEquals("Category not found", exception.getMessage());

        // Verify that the category repository was called
        verify(categoryRepository, times(1)).findByName("electronics");
        verify(productRepository, times(0)).findByCategory(any());
    }

    @Test
    void testGetAllProducts() {
        // Mock Pageable
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product));

        // Mock repository method
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // Call the service method
        Page<ProductDTO> productDTOPage = productService.getAllProducts(pageable);

        // Assertions
        assertNotNull(productDTOPage);
        assertEquals(1, productDTOPage.getTotalElements());
        assertEquals("Test Product", productDTOPage.getContent().get(0).getTitle());

        // Verify that the repository was called
        verify(productRepository, times(1)).findAll(pageable);
    }
}

