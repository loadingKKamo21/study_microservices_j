package com.programmingtechie.productservice.repositories.mybatis;

import com.programmingtechie.productservice.models.Product;
import com.programmingtechie.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    
    private final ProductMapper productMapper;
    
    @Override
    public Product save(Product product) {
        productMapper.save(product);
        return product;
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productMapper.findById(id));
    }
    
    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
    
    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);
    }
    
}
