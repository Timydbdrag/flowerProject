package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    OrderStatus create(OrderStatus orderStatus);
    OrderStatus update(OrderStatus orderStatus);
    List<OrderStatus> getAll();
}
