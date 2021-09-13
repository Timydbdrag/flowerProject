package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.User;
import com.greenstreet.warehouse.model.request.RequestUserDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User createUser(RequestUserDTO userDTO);
    User updateUser(RequestUserDTO userDTO);
    Optional<User> getUser(String login);
    Page<ResponseUserDTO> getUsers(Pageable pageable);
}
