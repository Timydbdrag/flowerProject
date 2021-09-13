package com.greenstreet.warehouse.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityUtilsTest {

    @BeforeEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("tester", "tester"));
        SecurityContextHolder.setContext(securityContext);
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        assertThat(login).contains("tester");
    }

    @Test
    void getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("tester", "token"));
        SecurityContextHolder.setContext(securityContext);
        Optional<String> jwt = SecurityUtils.getCurrentUserJWT();
        assertThat(jwt).contains("token");
    }

    @Test
    void hasCurrentUserThisAuthority() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        securityContext.setAuthentication(
                new UsernamePasswordAuthenticationToken("manager", "manager", authorities)
        );
        SecurityContextHolder.setContext(securityContext);

        assertThat(SecurityUtils.hasCurrentUserThisAuthority("ROLE_MANAGER")).isTrue();
        assertThat(SecurityUtils.hasCurrentUserThisAuthority("ROLE_ADMIN")).isFalse();
    }
}