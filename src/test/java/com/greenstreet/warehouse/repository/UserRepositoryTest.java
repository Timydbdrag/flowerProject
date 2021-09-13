package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EntityScan({"com.greenstreet.warehouse.entity"})
class UserRepositoryTest {

    private final static String TEST_EMAIL = "test@email.com";
    private final static String TEST_LOGIN = "admin";
    private final static String TEST_LOGIN_NOT_EXIST = "bug";

    @Autowired
    UserRepository userRepository;

    @DisplayName("Checking the table for the presence of users")
    @Test
    void collectionNotEmpty() {
        List<User> all = userRepository.findAll();
        assertTrue(all.size() > 0);
    }

    @DisplayName("Checking for existing emails")
    @Test
    void findOneByEmailIgnoreCase() {
        Optional<User> oneByEmailIgnoreCase = userRepository.findOneByEmailIgnoreCase(TEST_EMAIL);
        assertTrue(oneByEmailIgnoreCase.isPresent());
    }

    @DisplayName("Checking search by login, login exists")
    @Test
    void findOneByLogin() {
        Optional<User> oneByLogin = userRepository.findOneByLogin(TEST_LOGIN);
        assertTrue(oneByLogin.isPresent());
    }

    @DisplayName("Checking search by login, there is no login in the database")
    @Test
    void findOneByLogin_notFound() {
        Optional<User> oneByLogin = userRepository.findOneByLogin(TEST_LOGIN_NOT_EXIST);
        assertTrue(oneByLogin.isEmpty());
    }

}