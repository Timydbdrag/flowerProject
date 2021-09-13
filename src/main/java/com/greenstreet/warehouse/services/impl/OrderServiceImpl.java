package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.*;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.request.OrderDTO;
import com.greenstreet.warehouse.model.request.OrderDTOAdmin;
import com.greenstreet.warehouse.model.request.OrderProductDTO;
import com.greenstreet.warehouse.model.response.ResponseOrderDTO;
import com.greenstreet.warehouse.repository.*;
import com.greenstreet.warehouse.security.SecurityUtils;
import com.greenstreet.warehouse.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.greenstreet.warehouse.exception.ExceptionConstant.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final DeliveryScheduleRepository scheduleRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductStatusRepository orderProductStatusRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseOrderDTO create(OrderDTO orderDTO) {
        User user =  SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND));

        DeliverySchedule schedule = scheduleRepository.findById(orderDTO.getDeliveryScheduleId())
                .orElseThrow(() -> new ApiRequestException(DELIVERY_DATE_NOT_FOUND));

        OrderStatus orderStatus = orderStatusRepository.findById(1)
                .orElseThrow(() -> new ApiRequestException(STATUS_NOT_FOUND));

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUser(user);
        order.setDeliverySchedule(schedule);
        order.setStatus(orderStatus);

        Order saveOrder = orderRepository.save(order);

        Set<OrderProduct> orderProducts = orderDTO.getProducts()
                .stream()
                .map(orderProductDTO -> productRepository.findById(orderProductDTO.getProductId())
                        .map(product -> buildOrderProduct(orderProductDTO, product, saveOrder))
                        .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND)))
                .collect(Collectors.toSet());

        Set<OrderProduct> products = new HashSet<>(orderProductRepository.saveAll(orderProducts));

        saveOrder.setOrderProducts(products);

        return new ResponseOrderDTO(saveOrder);
    }

    @Override
    public ResponseOrderDTO update(OrderDTOAdmin orderDTO) {
        log.debug("Update order ID: {}", orderDTO.getId());

        Order order = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new ApiRequestException(ORDER_NOT_FOUND));

        if (!order.getStatus().getId().equals(orderDTO.getStatusId())) {
            OrderStatus orderStatus = orderStatusRepository.findById(orderDTO.getStatusId())
                    .orElseThrow(() -> new ApiRequestException(STATUS_NOT_FOUND));

            order.setStatus(orderStatus);
        }

        if(!order.getDeliverySchedule().getId().equals(orderDTO.getDeliveryId())) {
            DeliverySchedule schedule = scheduleRepository.findById(orderDTO.getDeliveryId())
                    .orElseThrow(() -> new ApiRequestException(DELIVERY_DATE_NOT_FOUND));

            order.setDeliverySchedule(schedule);
        }

        orderDTO.getProducts().forEach(this::updateOrderProduct);

        return new ResponseOrderDTO(orderRepository.save(order));
    }

    @Override
    public Page<ResponseOrderDTO> getAll(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable))
            throw new ApiRequestException(SORTED_PARAM_NOT_FOUND);

        log.debug("Getting all orders");
        return orderRepository.findAll(pageable).map(ResponseOrderDTO::new);
    }

    @Override
    public ResponseOrderDTO getOrder(UUID id) {
        log.debug("Getting order by id: " + id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ORDER_NOT_FOUND));

        return new ResponseOrderDTO(order);
    }

    private void updateOrderProduct(OrderProductDTO orderProductDTO) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductDTO.getId())
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND));


        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductDTO.getStatus())
                .orElseThrow(() -> new ApiRequestException(STATUS_NOT_FOUND));

        orderProduct.setStatus(orderProductStatus);
        orderProduct.setCount(orderProductDTO.getCount());

        orderProductRepository.save(orderProduct);
    }

    private OrderProduct buildOrderProduct(OrderProductDTO orderProductDTO, Product product, Order order) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(UUID.randomUUID());
        orderProduct.setProduct(product);
        orderProduct.setOrder(order);
        orderProduct.setCount(orderProductDTO.getCount());
        orderProduct.setPrice(product.getPrice());
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(1)
                .orElseThrow(() -> new ApiRequestException(STATUS_NOT_FOUND));
        orderProduct.setStatus(orderProductStatus);
        return orderProduct;
    }

    private List<String> getAllowedOrderedProperties() {
        return List.of(
                "id", "user", "deliverySchedule", "status"
        );
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort()
                .stream()
                .map(Sort.Order::getProperty)
                .allMatch(getAllowedOrderedProperties()::contains);
    }
}
