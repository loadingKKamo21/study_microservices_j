package com.programmingtechie.productservice.services;

import com.programmingtechie.productservice.dtos.ProductRequest;
import com.programmingtechie.productservice.dtos.ProductResponse;
import com.programmingtechie.productservice.models.Product;
import com.programmingtechie.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    @Transactional
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                                 .name(productRequest.getName())
                                 .description(productRequest.getDescription())
                                 .price(productRequest.getPrice())
                                 .build();
        
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }
    
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).collect(toList());
    }
    
    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
