package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
