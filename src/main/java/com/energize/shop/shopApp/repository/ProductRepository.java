package com.energize.shop.shopApp.repository;

import com.energize.shop.shopApp.repository.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findAllByCategory_Id(long categoryId);
    List<ProductModel> findAll();
    ProductModel findByName(String name);
 }
