package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    List<Role> getAll();
}
