package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.OrderProductStatus;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.repository.OrderProductStatusRepository;
import com.greenstreet.warehouse.services.OrderProductStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.greenstreet.warehouse.exception.ExceptionConstant.STATUS_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProductStatusServiceImpl implements OrderProductStatusService {

    private final OrderProductStatusRepository orderProductStatusRepository;

    @Override
    public OrderProductStatus create(OrderProductStatus status) {
        log.info("A new status for a product in an order has been added to the database, name: {} ", status.getName());
        status.setId(null);
        return orderProductStatusRepository.save(status);
    }

    @Override
    public OrderProductStatus update(OrderProductStatus status) {

        OrderProductStatus statusDB = orderProductStatusRepository.findById(status.getId())
                .orElseThrow(() -> new ApiRequestException(STATUS_NOT_FOUND));

        log.info("Status for a product in an order name {} changed to {}", statusDB.getName(), status.getName());
        statusDB.setName(status.getName());

        return orderProductStatusRepository.save(statusDB);
    }

    @Override
    public List<OrderProductStatus> getAll() {
        return orderProductStatusRepository.findAll();
    }
}
