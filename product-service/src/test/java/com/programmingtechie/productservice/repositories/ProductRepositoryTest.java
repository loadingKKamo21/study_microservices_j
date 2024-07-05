package com.programmingtechie.productservice.repositories;

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
class ProductRepositoryTest {
    
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductMapper     productMapper;
    
    @Test
    @DisplayName("save")
    void save() {
        //Given
        Product product = getProduct();
        
        //When
        Product savedProduct = productRepository.save(product);
        
        //Then
        Product findProduct = productMapper.findById(savedProduct.getId());
        
        assertThat(findProduct.getId()).isEqualTo(savedProduct.getId());
        assertThat(findProduct.getName()).isEqualTo(savedProduct.getName());
        assertThat(findProduct.getDescription()).isEqualTo(savedProduct.getDescription());
        assertThat(findProduct.getPrice()).isEqualTo(savedProduct.getPrice());
    }
    
    @Test
    @DisplayName("findById")
    void findById() {
        //Given
        Product product = getProduct();
        productMapper.save(product);
        Long id = product.getId();
        
        //When
        Product findProduct = productRepository.findById(id).get();
        
        //Then
        assertThat(findProduct.getId()).isEqualTo(product.getId());
        assertThat(findProduct.getName()).isEqualTo(product.getName());
        assertThat(findProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(findProduct.getPrice()).isEqualTo(product.getPrice());
    }
    
    @Test
    @DisplayName("findAll")
    void findAll() {
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
        List<Product> findProducts = productRepository.findAll()
                                                      .stream().sorted(comparing(Product::getId)).collect(toList());
        
        //Then
        List<Product> products = productMapper.findAll();
        for (int i = 0; i < size; i++) {
            Product product     = products.get(i);
            Product findProduct = findProducts.get(i);
            
            assertThat(findProduct.getId()).isEqualTo(product.getId());
            assertThat(findProduct.getName()).isEqualTo(product.getName());
            assertThat(findProduct.getDescription()).isEqualTo(product.getDescription());
            assertThat(findProduct.getPrice()).isEqualTo(product.getPrice());
        }
    }
    
    @Test
    @DisplayName("deleteById")
    void deleteById() {
        //Given
        Product product = getProduct();
        productMapper.save(product);
        Long id = product.getId();
        
        //When
        productRepository.deleteById(id);
        
        //Then
        Product deletedProduct = productMapper.findById(id);
        
        assertThat(deletedProduct).isNull();
    }
    
    private Product getProduct() {
        return Product.builder()
                      .name("iPhone 13")
                      .description("iPhone 13")
                      .price(1200.0)
                      .build();
    }
    
}