package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.services.RoleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoleService roleService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void createRole() throws Exception {
        String uri = "/api/role";
        Role role_test = new Role(1L, "ROLE_TEST");

        given(roleService.createRole(any())).willReturn(role_test);

        String expectedResult = objectMapper.writeValueAsString(role_test);

        mockMvc.perform(post(uri)
                .content(objectMapper.writeValueAsString(new Role()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResult));
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void getAllRoles() throws Exception {
        String uri = "/api/role";

        List<Role> roles_test = List.of(new Role(1L, "ROLE_TEST"));
        given(roleService.getAll()).willReturn(roles_test);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(roles_test)));
    }

    @Test
    void getAllRoles_shouldReturnStatus_403() throws Exception {
        String uri = "/api/role";

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


}