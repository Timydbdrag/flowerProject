package com.greenstreet.warehouse.config;

import com.greenstreet.warehouse.security.filter.CustomAuthenticationFilter;
import com.greenstreet.warehouse.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomAuthorizationFilter customAuthorizationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                .antMatchers("/api/account").authenticated()
                .antMatchers("/api/country/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/api/color/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/api/schedule/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/api/order/status/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/api/order/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/api/order/product/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/api/user/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/role/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
