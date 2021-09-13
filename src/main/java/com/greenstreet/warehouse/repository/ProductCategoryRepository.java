package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
