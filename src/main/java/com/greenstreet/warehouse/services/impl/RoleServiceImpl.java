package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.repository.RoleRepository;
import com.greenstreet.warehouse.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        log.info("Save new role to the DB, name: {} ", role.getName());
        role.setId(null);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
