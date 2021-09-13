package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.entity.User;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.UserStatus;
import com.greenstreet.warehouse.model.request.RequestUserDTO;
import com.greenstreet.warehouse.repository.RoleRepository;
import com.greenstreet.warehouse.repository.UserRepository;
import com.greenstreet.warehouse.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.greenstreet.warehouse.exception.ExceptionConstant.EMAIL_ALREADY;
import static com.greenstreet.warehouse.exception.ExceptionConstant.LOGIN_ALREADY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void createUser() {
        User testUser = getTestUser();

        given(userRepository.findOneByLogin(any())).willReturn(Optional.empty());
        given(userRepository.findOneByEmailIgnoreCase(any())).willReturn(Optional.empty());
        given(roleRepository.findByName(any())).willReturn(Optional.of(getRoleClient()));
        given(userRepository.save(any())).willReturn(testUser);

        assertThat(userService.createUser(new RequestUserDTO(testUser))).isEqualTo(testUser);
    }

    @Test
    void createUserException_loginExists() {
        User testUser = getTestUser();

        given(userRepository.findOneByLogin(any())).willReturn(Optional.of(testUser));

        RequestUserDTO userDTO = new RequestUserDTO(testUser);
        Exception exception = assertThrows(ApiRequestException.class, () -> {
            userService.createUser(userDTO);
        });

        assertThat(exception.getMessage()).isEqualTo(LOGIN_ALREADY);
    }

    @Test
    void createUserException_emailExists() {
        User testUser = getTestUser();

        given(userRepository.findOneByLogin(any())).willReturn(Optional.empty());
        given(userRepository.findOneByEmailIgnoreCase(any())).willReturn(Optional.of(testUser));

        RequestUserDTO userDTO = new RequestUserDTO(testUser);
        Exception exception = assertThrows(ApiRequestException.class, () -> {
            userService.createUser(userDTO);
        });

        assertThat(exception.getMessage()).isEqualTo(EMAIL_ALREADY);
    }

    @Test
    void updateUser() {
        User testUser = getTestUser();
        RequestUserDTO requestUserDTO = new RequestUserDTO(testUser);

        given(userRepository.findById(requestUserDTO.getId())).willReturn(Optional.of(testUser));
        given(userRepository.save(testUser)).willReturn(testUser);
        given(roleRepository.findByName(any())).willReturn(Optional.of(getRoleClient()));

        assertThat(userService.updateUser(requestUserDTO)).isEqualTo(testUser);
    }

    @Test
    void updateUserException_ID_null() {
        RequestUserDTO userDTO = new RequestUserDTO();
        Exception exception = assertThrows(ApiRequestException.class, () -> {
            userService.updateUser(userDTO);
        });

        String expectedMessage = "User ID cannot be null!";

        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void getUser() {
        String login = "testUser";
        given(userRepository.findOneByLogin(login))
                .willReturn(Optional.of(getTestUser()));
        Optional<User> user = userService.getUser(login);

        assertThat(user.isPresent()).isSameAs(true);
        assertThat(user.get().getLogin()).isEqualTo(login);
    }

    @Test
    void getUsers() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));

        List<User> userList = List.of(getTestUser());
        Page<User> users = new PageImpl<>(userList);

        given(userRepository.findAll(pageable)).willReturn(users);

        assertThat(userService.getUsers(pageable)).isNotEmpty();
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