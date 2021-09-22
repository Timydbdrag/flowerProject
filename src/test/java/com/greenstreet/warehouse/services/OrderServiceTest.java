package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.*;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.request.OrderDTO;
import com.greenstreet.warehouse.model.request.OrderDTOAdmin;
import com.greenstreet.warehouse.model.request.OrderProductDTO;
import com.greenstreet.warehouse.model.response.ResponseOrderDTO;
import com.greenstreet.warehouse.repository.*;
import com.greenstreet.warehouse.security.SecurityUtils;
import com.greenstreet.warehouse.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.greenstreet.warehouse.exception.ExceptionConstant.ORDER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    DeliveryScheduleRepository scheduleRepository;
    @Mock
    OrderStatusRepository orderStatusRepository;
    @Mock
    OrderProductRepository orderProductRepository;
    @Mock
    OrderProductStatusRepository orderProductStatusRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void shouldCreatedOrder() {
        Order testOrder = getTestOrder();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDeliveryScheduleId(getSchedule().getId());
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProductId(1L);
        orderDTO.setProducts(new HashSet<>(Collections.singletonList(orderProductDTO)));

        try (MockedStatic<SecurityUtils> theMock = Mockito.mockStatic(SecurityUtils.class)) {
            theMock.when(SecurityUtils::getCurrentUserLogin)
                    .thenReturn(Optional.of(testOrder.getUser().getLogin()));

            given(userRepository.findOneByLogin(testOrder.getUser().getLogin())).willReturn(Optional.of(getUser()));

            given(scheduleRepository.findById(any())).willReturn(Optional.of(getSchedule()));
            given(orderStatusRepository.findById(any())).willReturn(Optional.of(getOrderStatus()));
            given(orderRepository.save(any())).willReturn(testOrder);
            given(productRepository.findById(any())).willReturn(Optional.of(getProduct()));
            given(orderProductStatusRepository.findById(1)).willReturn(Optional.of(getOrderProductStatus()));
            given(orderProductRepository.saveAll(any()))
                    .willReturn(new ArrayList<>(Collections.singletonList(getOrderProduct())));

            ResponseOrderDTO actual = orderService.create(orderDTO);
            ResponseOrderDTO expected = new ResponseOrderDTO(testOrder);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    void shouldUpdatedProduct() {
        Order testOrder = getTestOrder();
        OrderDTOAdmin orderDTOAdmin = new OrderDTOAdmin();
        orderDTOAdmin.setId(testOrder.getId());
        orderDTOAdmin.setDeliveryId(testOrder.getDeliverySchedule().getId());
        orderDTOAdmin.setStatusId(testOrder.getStatus().getId());
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProductId(1L);
        orderProductDTO.setStatus(1);
        orderDTOAdmin.setProducts(new HashSet<>(Collections.singletonList(orderProductDTO)));

        given(orderRepository.findById(any())).willReturn(Optional.of(testOrder));
        given(orderProductRepository.findById(any())).willReturn(Optional.of(getOrderProduct()));
        given(orderProductStatusRepository.findById(1)).willReturn(Optional.of(getOrderProductStatus()));
        given(orderRepository.save(any())).willReturn(testOrder);

        assertThat(orderService.update(orderDTOAdmin)).isEqualTo(new ResponseOrderDTO(testOrder));
    }

    @Test
    void shouldReturnPageOrders() {
        List<Order> orderList = List.of(getTestOrder());
        Page<Order> page = new PageImpl<>(orderList);

        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));
        given(orderRepository.findAll(pageable)).willReturn(page);

        List<ResponseOrderDTO> expected = orderList.stream().map(ResponseOrderDTO::new)
                .collect(Collectors.toList());

        assertThat(orderService.getAll(pageable)).isEqualTo(new PageImpl<>(expected));
    }

    @Test
    void shouldReturnOrderById() {
        Order testOrder = getTestOrder();

        given(orderRepository.findById(testOrder.getId())).willReturn(Optional.of(testOrder));

        assertThat(orderService.getOrder(testOrder.getId())).isEqualTo(new ResponseOrderDTO(testOrder));
    }

    @Test
    void shouldReturnExceptionWhenFindById() {
        OrderDTOAdmin orderDTOAdmin = new OrderDTOAdmin();
        orderDTOAdmin.setId(UUID.randomUUID());

        Exception exception = assertThrows(ApiRequestException.class, () -> {
            orderService.update(orderDTOAdmin);
        });

        assertThat(exception.getMessage()).isEqualTo(ORDER_NOT_FOUND);
    }

    @Test
    void shouldReturnOrdersByCurrentUser(){
        Order testOrder = getTestOrder();
        HashSet<Order> orders = new HashSet<>(Collections.singletonList(testOrder));

        try (MockedStatic<SecurityUtils> theMock = Mockito.mockStatic(SecurityUtils.class)) {
            theMock.when(SecurityUtils::getCurrentUserLogin)
                    .thenReturn(Optional.of(testOrder.getUser().getLogin()));

            given(orderRepository.getUserOrders(testOrder.getUser().getLogin())).willReturn(orders);

            Set<ResponseOrderDTO> expected = orders.stream().map(ResponseOrderDTO::new).collect(Collectors.toSet());
            assertThat(orderService.getUserOrders()).isEqualTo(expected);
        }
    }

    private Order getTestOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUser(getUser());
        order.setDeliverySchedule(getSchedule());
        order.setStatus(getOrderStatus());
        order.setOrderProducts(new HashSet<>(Collections.singletonList(getOrderProduct())));
        return order;
    }

    private User getUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin("Smith");
        return user;
    }

    private DeliverySchedule getSchedule() {
        return new DeliverySchedule(1, LocalDate.now());
    }

    private OrderStatus getOrderStatus(){
        return new OrderStatus(1, "В обработке");
    }

    private OrderProductStatus getOrderProductStatus(){
        return new OrderProductStatus(1, "Ожидает подтверждения");
    }

    private Product getProduct(){
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.00);
        product.setImgLink("link");
        return product;
    }

    private OrderProduct getOrderProduct(){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(UUID.randomUUID());
        orderProduct.setPrice(100.00);
        orderProduct.setCount(50);
        orderProduct.setProduct(getProduct());
        orderProduct.setStatus(getOrderProductStatus());
        return orderProduct;
    }
}