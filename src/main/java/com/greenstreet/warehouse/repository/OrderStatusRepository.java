package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Integer> {
}
