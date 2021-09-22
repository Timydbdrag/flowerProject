package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.model.request.OrderDTO;
import com.greenstreet.warehouse.model.request.OrderDTOAdmin;
import com.greenstreet.warehouse.model.response.ResponseOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface OrderService {
    ResponseOrderDTO create(OrderDTO orderDTO);
    ResponseOrderDTO update (OrderDTOAdmin order);
    Page<ResponseOrderDTO> getAll(Pageable pageable);
    ResponseOrderDTO getOrder(UUID id);
    Set<ResponseOrderDTO> getUserOrders();
}
