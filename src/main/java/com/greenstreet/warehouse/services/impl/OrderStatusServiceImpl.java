package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.OrderStatus;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.repository.OrderStatusRepository;
import com.greenstreet.warehouse.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.greenstreet.warehouse.exception.ExceptionConstant.STATUS_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Override
    public OrderStatus create(OrderStatus orderStatus) {
        log.info("A new order status has been added to the database, name: {} ", orderStatus.getName());
        orderStatus.setId(null);
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public OrderStatus update(OrderStatus orderStatus) {
        if(orderStatus.getId() == 1) throw new ApiRequestException("Status with ID 1 cannot be changed!");

        OrderStatus statusDB = orderStatusRepository.findById(orderStatus.getId())
                .orElseThrow(() -> new ApiRequestException(STATUS_NOT_FOUND));

        if(!statusDB.getName().equals(orderStatus.getName())) {
            log.info("Order status name {} changed to {}", statusDB.getName(), orderStatus.getName());
            statusDB.setName(orderStatus.getName());

            return orderStatusRepository.save(statusDB);
        }

        return orderStatus;
    }

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepository.findAll();
    }
}
