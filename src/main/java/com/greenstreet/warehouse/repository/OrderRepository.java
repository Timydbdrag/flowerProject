package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {


    @Query("SELECT o FROM Order o WHERE o.user.login = ?1")
    Set<Order> getUserOrders(String login);
}
