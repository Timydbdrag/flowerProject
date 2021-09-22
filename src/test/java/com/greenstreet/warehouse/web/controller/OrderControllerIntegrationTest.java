package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.WarehouseApplication;
import com.greenstreet.warehouse.entity.*;
import com.greenstreet.warehouse.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseApplication.class)
class OrderControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    OrderRepository orderRepository;

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldReturnPageOrders() throws Exception {
        String uri = "/api/order?page=0&size=2";

        Pageable pageable = PageRequest.of(0, 2);

        List<Order> testOrder = List.of(getTestOrder());
        Page<Order> orderPage = new PageImpl<>(testOrder);

        given(orderRepository.findAll(pageable)).willReturn(orderPage);

        OrderProduct orderProduct = testOrder.get(0).getOrderProducts().stream().findFirst().orElse(null);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[*].id").value(testOrder.get(0).getId().toString()))
                .andExpect(jsonPath("$.content.[*].status.id").value(testOrder.get(0).getStatus().getId()))
                .andExpect(jsonPath("$.content.[*].status.name").
                        value(testOrder.get(0).getStatus().getName()))
                .andExpect(jsonPath("$.content.[*].deliveryDate.id")
                        .value(testOrder.get(0).getDeliverySchedule().getId()))
                .andExpect(jsonPath("$.content.[*].deliveryDate.date")
                        .value(testOrder.get(0).getDeliverySchedule().getDate().toString()))
                .andExpect(jsonPath("$.content.[*].products.[*].id").value(orderProduct.getId().toString()))
                .andExpect(jsonPath("$.content.[*].products.[*].price").value(orderProduct.getPrice()))
                .andExpect(jsonPath("$.content.[*].products.[*].count").value(orderProduct.getCount()))
                .andExpect(jsonPath("$.content.[*].products.[*].status.id")
                        .value(orderProduct.getStatus().getId()))
                .andExpect(jsonPath("$.content.[*].products.[*].status.name")
                        .value(orderProduct.getStatus().getName()));
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

    private OrderStatus getOrderStatus() {
        return new OrderStatus(1, "In processing");
    }

    private OrderProductStatus getOrderProductStatus() {
        return new OrderProductStatus(1, "Awaiting confirmation");
    }

    private Product getProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.00);
        product.setImgLink("link");
        return product;
    }

    private OrderProduct getOrderProduct() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(UUID.randomUUID());
        orderProduct.setPrice(100.00);
        orderProduct.setCount(50);
        orderProduct.setProduct(getProduct());
        orderProduct.setStatus(getOrderProductStatus());
        return orderProduct;
    }
}