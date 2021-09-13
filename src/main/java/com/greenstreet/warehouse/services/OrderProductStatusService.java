package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.OrderProductStatus;

import java.util.List;

public interface OrderProductStatusService {
    OrderProductStatus create(OrderProductStatus orderProductStatus);
    OrderProductStatus update(OrderProductStatus orderProductStatus);
    List<OrderProductStatus> getAll();
}
