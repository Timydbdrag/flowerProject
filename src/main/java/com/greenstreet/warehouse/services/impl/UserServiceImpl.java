package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.entity.User;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.UserStatus;
import com.greenstreet.warehouse.model.request.RequestUserDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import com.greenstreet.warehouse.repository.RoleRepository;
import com.greenstreet.warehouse.repository.UserRepository;
import com.greenstreet.warehouse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.greenstreet.warehouse.exception.ExceptionConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(RequestUserDTO userDTO) {
        if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new ApiRequestException(LOGIN_ALREADY);
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new ApiRequestException(EMAIL_ALREADY);
        } else {
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setLogin(userDTO.getLogin().toLowerCase());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail().toLowerCase());
            user.setUserStatus(UserStatus.ACTIVE);

            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encryptedPassword);

            if (userDTO.getRoles() != null) {
                Set<Role> authorities = userDTO
                        .getRoles()
                        .stream()
                        .map(roleRepository::findByName)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
                user.setRoles(authorities);
            }

            log.info("Save new user to the DB, login: {} ", userDTO.getLogin());
            return userRepository.save(user);
        }
    }

    @Override
    public User updateUser(RequestUserDTO userDTO) {
        if (userDTO.getId() == null) throw new ApiRequestException("User ID cannot be null!");

        return Optional.of(userRepository.findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    if (!user.getEmail().equals(userDTO.getEmail()) &&
                            userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent())
                        throw new ApiRequestException(EMAIL_ALREADY);

                    if (!user.getLogin().equals(userDTO.getLogin()) &&
                            userRepository.findOneByLogin(userDTO.getLogin()).isPresent())
                        throw new ApiRequestException(LOGIN_ALREADY);

                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail().toLowerCase());

                    if (userDTO.getRoles() != null) {
                        Set<Role> authorities = userDTO
                                .getRoles()
                                .stream()
                                .map(roleRepository::findByName)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toSet());
                        user.setRoles(authorities);
                    }

                    if (userDTO.getUserStatus() != null) {
                        Arrays.stream(UserStatus.values())
                                .filter(s -> s.toString().equals(userDTO.getUserStatus()))
                                .findFirst()
                                .ifPresent(user::setUserStatus);
                    }

                    log.info("Changing user in the database, login: {} ", userDTO.getLogin());
                    return userRepository.save(user);

                })
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND));
    }

    @Override
    public Optional<User> getUser(String login) {
        log.debug("Getting user of login: " + login);
        return Optional.ofNullable(userRepository.findOneByLogin(login)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found.");
                }));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseUserDTO> getUsers(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable))
            throw new ApiRequestException(SORTED_PARAM_NOT_FOUND);

        log.debug("Getting all users");
        return userRepository.findAll(pageable).map(ResponseUserDTO::new);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> optional = Optional.ofNullable(userRepository.findOneByLogin(login)
                .orElseThrow(() -> {
                    throw new ApiRequestException(USER_NOT_FOUND);
                }));

        if(optional.isPresent()) {
            User user = optional.get();

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(el -> authorities.add(new SimpleGrantedAuthority(el.getName())));

            return new org.springframework.security.core.userdetails
                    .User(user.getLogin(), user.getPassword(), authorities);
        }
        return null;
    }

    private List<String> getAllowedOrderedProperties() {
        return List.of(
                "id", "login", "firstName", "lastName", "email", "createdBy", "createdDate", "updatedBy", "updatedDate"
        );
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort()
                .stream()
                .map(Sort.Order::getProperty)
                .allMatch(getAllowedOrderedProperties()::contains);
    }
}
