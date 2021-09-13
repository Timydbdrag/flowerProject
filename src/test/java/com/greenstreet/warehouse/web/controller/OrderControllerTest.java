package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.entity.DeliverySchedule;
import com.greenstreet.warehouse.entity.OrderStatus;
import com.greenstreet.warehouse.model.request.OrderDTO;
import com.greenstreet.warehouse.model.request.OrderDTOAdmin;
import com.greenstreet.warehouse.model.request.OrderProductDTO;
import com.greenstreet.warehouse.model.response.ResponseOrderDTO;
import com.greenstreet.warehouse.model.response.ResponseOrderProductDTO;
import com.greenstreet.warehouse.services.OrderService;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    OrderService orderService;

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldReturnPageOrders() throws Exception {
        String uri = "/api/order?page=0&size=2";

        Pageable pageable = PageRequest.of(0,2);
        List<ResponseOrderDTO> responseOrderDTOList = List.of(getResponseOrderDTO());
        Page<ResponseOrderDTO> dtoPage = new PageImpl<>(responseOrderDTOList);

        given(orderService.getAll(pageable)).willReturn(dtoPage);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dtoPage)));
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldReturnOrderById() throws Exception {
        ResponseOrderDTO orderDTO = getResponseOrderDTO();

        String uri = "/api/order/"+ orderDTO.getId();

        given(orderService.getOrder(orderDTO.getId())).willReturn(orderDTO);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(orderDTO)));
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldCreatedOrder() throws Exception {
        String uri = "/api/order";

        ResponseOrderDTO responseOrderDTO = getResponseOrderDTO();

        given(orderService.create(any())).willReturn(responseOrderDTO);

        String order = objectMapper.writeValueAsString(getOrderDTO());

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(order))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(responseOrderDTO)));
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void updateOrder() throws Exception {
        String uri = "/api/order";

        ResponseOrderDTO expected = getResponseOrderDTO();
        given(orderService.update(any())).willReturn(expected);

        String expectedResult = objectMapper.writeValueAsString(expected);

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new OrderDTOAdmin())))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    private ResponseOrderDTO getResponseOrderDTO() {
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setId(UUID.randomUUID());
        responseOrderDTO.setStatus(new OrderStatus(1, "order status"));
        responseOrderDTO.setDeliveryDate(getDeliveryDate());

        ResponseOrderProductDTO responseOrderProductDTO = new ResponseOrderProductDTO();
        responseOrderProductDTO.setId(UUID.randomUUID());
        responseOrderProductDTO.setProductId(1L);
        responseOrderProductDTO.setCount(1);

        responseOrderDTO.setProducts(new HashSet<>(Collections.singletonList(responseOrderProductDTO)));

        return responseOrderDTO;
    }

    private OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setOrderID(UUID.randomUUID());
        orderDTO.setProducts(new HashSet<>(Collections.singletonList(orderProductDTO)));
        orderDTO.setDeliveryScheduleId(getDeliveryDate().getId());
        return orderDTO;
    }

    private DeliverySchedule getDeliveryDate() {
        return new DeliverySchedule(1, LocalDate.now());
    }
}