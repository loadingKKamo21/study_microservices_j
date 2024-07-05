package com.programmingtechie.inventoryservice.repositories.mybatis;

import com.programmingtechie.inventoryservice.models.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InventoryMapper {
    void save(Inventory param);
    
    Inventory findById(Long id);
    
    Inventory findBySkuCode(String skuCode);
    
    List<Inventory> findAll();
    
    void deleteById(Long id);
    
    List<Inventory> findAllBySkuCodeIn(@Param("params") List<String> params);
}
