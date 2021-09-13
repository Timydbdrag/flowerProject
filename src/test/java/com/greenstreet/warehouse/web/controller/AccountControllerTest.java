package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.model.request.PasswordChangeDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import com.greenstreet.warehouse.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static com.greenstreet.warehouse.config.Constants.PASSWORD_CHANGED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    AccountService accountService;

    @Test
    @WithMockUser(value = "tester")
    void getAccount() throws Exception {
        String uri = "/api/account";

        ResponseUserDTO userDTO = getUserDTO();
        given(accountService.getUserWithAuthorities()).willReturn(userDTO);

        String expectedResult = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    @WithMockUser(value = "tester")
    void changePassword() throws Exception {
        String uri = "/api/account/change-password";

        given(accountService.changePassword(any())).willReturn(PASSWORD_CHANGED);

        mockMvc.perform(put(uri)
                .content(objectMapper.writeValueAsString(new PasswordChangeDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(PASSWORD_CHANGED));
    }

    private ResponseUserDTO getUserDTO() {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(UUID.randomUUID());
        responseUserDTO.setLogin("Shrek");
        return responseUserDTO;
    }
}