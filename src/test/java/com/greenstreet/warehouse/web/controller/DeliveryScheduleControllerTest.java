package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.entity.DeliverySchedule;
import com.greenstreet.warehouse.services.DeliveryScheduleService;
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
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryScheduleControllerTest {

    private final String uri = "/api/schedule";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    DeliveryScheduleService scheduleService;

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void shouldReturnAllDeliveryDate() throws Exception {
        Pageable pageable = PageRequest.of(0,2);
        Page<DeliverySchedule> page = new PageImpl<>(List.of(getTestSchedule()));
        given(scheduleService.getAll(pageable)).willReturn(page);

        mockMvc.perform(get(uri+"?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void shouldCreateDates() throws Exception {
        DeliverySchedule schedule = getTestSchedule();
        given(scheduleService.create(schedule)).willReturn(schedule);

        String date = objectMapper.writeValueAsString(schedule);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(date))
                .andExpect(status().isCreated())
                .andExpect(content().json(date));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void shouldUpdatedDates() throws Exception {
        DeliverySchedule schedule = getTestSchedule();
        given(scheduleService.update(schedule)).willReturn(schedule);

        String date = objectMapper.writeValueAsString(schedule);

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(date))
                .andExpect(status().isOk())
                .andExpect(content().json(date));
    }

    @Test
    @WithMockUser(value = "tester", roles = "CLIENT")
    void shouldReturnStatus_403_whenUpdatedDates() throws Exception {
        String date = objectMapper.writeValueAsString(getTestSchedule());

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(date))
                .andExpect(status().isForbidden());
    }

    private DeliverySchedule getTestSchedule() {
        return new DeliverySchedule(1, LocalDate.now());
    }
}