package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
}
