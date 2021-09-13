package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
