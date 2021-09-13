package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.repository.RoleRepository;
import com.greenstreet.warehouse.services.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    void shouldCreatedRole() {
        given(roleRepository.save(any())).willReturn(getTestRole());

        Role expect = getTestRole();
        Role request = new Role();
        request.setName(expect.getName());
        assertThat(roleService.createRole(request)).isEqualTo(expect);
    }

    private Role getTestRole() {
        return new Role(1L, "ROLE_TEST");
    }
}