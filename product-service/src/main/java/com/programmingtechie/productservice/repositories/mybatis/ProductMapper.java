package com.programmingtechie.productservice.repositories.mybatis;

import com.programmingtechie.productservice.models.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    void save(Product param);
    
    Product findById(Long id);
    
    List<Product> findAll();
    
    void deleteById(Long id);
}
