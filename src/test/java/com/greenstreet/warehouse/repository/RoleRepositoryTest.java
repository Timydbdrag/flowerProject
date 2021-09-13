package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan({"com.greenstreet.warehouse.entity"})
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void findByName() {
        List<Role> all = roleRepository.findAll();
        assertTrue(all.size() > 1);

        Optional<Role> role_admin = roleRepository.findByName("ROLE_ADMIN");
        assertTrue(role_admin.isPresent());
    }
}