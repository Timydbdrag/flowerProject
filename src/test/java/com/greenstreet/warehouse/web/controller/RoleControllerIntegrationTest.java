package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.WarehouseApplication;
import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseApplication.class)
class RoleControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    RoleRepository roleRepository;

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void createRole() throws Exception {
        String uri = "/api/role";
        Role roleTest = new Role(4L, "ROLE_TESTER");

        String expectedResult = objectMapper.writeValueAsString(roleTest);

        mockMvc.perform(post(uri)
                .content(objectMapper.writeValueAsString(roleTest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResult));

        Optional<Role> byName = roleRepository.findByName(roleTest.getName());
        assertThat(byName).isPresent();
        assertThat(byName.get().getName()).isEqualTo(roleTest.getName());
    }

}