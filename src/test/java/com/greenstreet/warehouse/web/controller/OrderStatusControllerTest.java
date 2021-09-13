package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.entity.OrderStatus;
import com.greenstreet.warehouse.services.OrderStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderStatusControllerTest {

    private final String uri = "/api/order/status";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    OrderStatusService orderStatusService;

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void shouldReturnAllStatus() throws Exception {
        List<OrderStatus> testStatuses = List.of(getTestStatus());
        given(orderStatusService.getAll()).willReturn(testStatuses);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testStatuses)));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void shouldCreatedStatus() throws Exception {
        OrderStatus testStatus = getTestStatus();
        given(orderStatusService.create(testStatus)).willReturn(testStatus);

        String status = objectMapper.writeValueAsString(testStatus);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(status))
                .andExpect(status().isCreated())
                .andExpect(content().json(status));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void shouldUpdatedStatus() throws Exception {
        OrderStatus testStatus = getTestStatus();
        given(orderStatusService.update(testStatus)).willReturn(testStatus);

        String status = objectMapper.writeValueAsString(testStatus);

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(status))
                .andExpect(status().isOk())
                .andExpect(content().json(status));
    }

    @Test
    @WithMockUser(value = "tester", roles = "CLIENT")
    void shouldReturnStatus_403_whenUpdatedStatus() throws Exception {
        String status = objectMapper.writeValueAsString(getTestStatus());

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(status))
                .andExpect(status().isForbidden());
    }

    private OrderStatus getTestStatus() {
        return new OrderStatus(1, "In Work");
    }
}