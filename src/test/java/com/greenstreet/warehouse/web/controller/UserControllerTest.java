package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.entity.User;
import com.greenstreet.warehouse.model.UserStatus;
import com.greenstreet.warehouse.model.request.RequestUserDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import com.greenstreet.warehouse.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    UserService userService;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldReturnPageUsers() throws Exception {
        String uri = "/api/user/?page=0&size=2";

        Pageable pageable = PageRequest.of(0,2);
        List<ResponseUserDTO> testUserList = List.of(new ResponseUserDTO(getTestUser()));
        Page<ResponseUserDTO> page = new PageImpl<>(testUserList);

        given(userService.getUsers(pageable)).willReturn(page);

        String expectedResult = objectMapper.writeValueAsString(page);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldReturnUserByLogin() throws Exception {
        String uri = "/api/user/tester";

        User responseUser = getTestUser();
        given(userService.getUser("tester")).willReturn(Optional.of(responseUser));

        String expectedResult = objectMapper.writeValueAsString(responseUser);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldCreatedUser() throws Exception{
        String uri = "/api/user";

        User saveUser = getTestUser();
        given(userService.createUser(any())).willReturn(saveUser);

        String expectedResult = objectMapper.writeValueAsString(saveUser);

        RequestUserDTO requestUserDTO = new RequestUserDTO(saveUser);
        requestUserDTO.setPassword("newPassword");

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResult));
    }

    @Test
    @WithMockUser(value = "tester", roles = "CLIENT")
    void createUserShouldReturnStatus_403() throws Exception{
        String uri = "/api/user";

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RequestUserDTO())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void createUserShouldReturnStatus_400() throws Exception{
        String uri = "/api/user";

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RequestUserDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void updateUserShouldReturnStatus_400()throws Exception {
        String uri = "/api/user";

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RequestUserDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "tester", roles = "CLIENT")
    void updateUserShouldReturnStatus_403()throws Exception {
        String uri = "/api/user";

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RequestUserDTO())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "tester", roles = "ADMIN")
    void shouldUpdatedUser()throws Exception {
        String uri = "/api/user";

        User updateUser = getTestUser();
        given(userService.updateUser(any())).willReturn(updateUser);

        String expectedResult = objectMapper.writeValueAsString(updateUser);

        RequestUserDTO requestUserDTO = new RequestUserDTO(updateUser);
        requestUserDTO.setPassword("newPassword");

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestUserDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    private User getTestUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin("testUser");
        user.setFirstName("Dmitriy");
        user.setLastName("Smith");
        user.setEmail("test@email.com");
        user.setPassword("Qwerty");
        user.setUserStatus(UserStatus.ACTIVE);

        Set<Role> roles = new HashSet<>();
        roles.add(getRoleClient());
        user.setRoles(roles);

        return user;
    }

    private Role getRoleClient(){
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_CLIENT");
        return role;
    }
}