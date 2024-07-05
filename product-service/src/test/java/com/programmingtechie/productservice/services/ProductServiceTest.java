package com.programmingtechie.productservice.services;

import com.programmingtechie.productservice.dtos.ProductRequest;
import com.programmingtechie.productservice.dtos.ProductResponse;
import com.programmingtechie.productservice.models.Product;
import com.programmingtechie.productservice.repositories.mybatis.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductServiceTest {
    
    @Autowired
    ProductService productService;
    @Autowired
    ProductMapper  productMapper;
    
    @Test
    @DisplayName("createProduct")
    void createProduct() {
        //Given
        ProductRequest productRequest = getProductRequest();
        
        //When
        productService.createProduct(productRequest);
        
        //Then
        Product findProduct = productMapper.findAll().get(0);
        
        assertThat(findProduct.getName()).isEqualTo(productRequest.getName());
        assertThat(findProduct.getDescription()).isEqualTo(productRequest.getDescription());
        assertThat(findProduct.getPrice()).isEqualTo(productRequest.getPrice());
    }
    
    @Test
    @DisplayName("getAllProducts")
    void getAllProducts() {
        //Given
        Random random = new Random();
        
        int size = random.nextInt(20) + 10;
        for (int i = 1; i <= size; i++) {
            Product product = Product.builder()
                                     .name("Product" + String.format("%0" + String.valueOf(size).length() + "d", i))
                                     .description("Product" + String.format("%0" + String.valueOf(size).length() + "d", i))
                                     .price(random.nextInt(100) + 10.0)
                                     .build();
            productMapper.save(product);
        }
        
        //When
        List<ProductResponse> findProductResponses = productService.getAllProducts().stream()
                                                                   .sorted(comparing(ProductResponse::getId))
                                                                   .collect(toList());
        
        //Then
        List<Product> products = productMapper.findAll();
        for (int i = 0; i < size; i++) {
            Product         product             = products.get(i);
            ProductResponse findProductResponse = findProductResponses.get(i);
            
            assertThat(findProductResponse.getId()).isEqualTo(product.getId());
            assertThat(findProductResponse.getName()).isEqualTo(product.getName());
            assertThat(findProductResponse.getDescription()).isEqualTo(product.getDescription());
            assertThat(findProductResponse.getPrice()).isEqualTo(product.getPrice());
        }
    }
    
    private ProductRequest getProductRequest() {
        return new ProductRequest("iPhone 13", "iPhone 13", 1200.0);
    }
    
}