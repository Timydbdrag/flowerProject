package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.OrderProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductStatusRepository extends JpaRepository<OrderProductStatus,Integer> {
}
