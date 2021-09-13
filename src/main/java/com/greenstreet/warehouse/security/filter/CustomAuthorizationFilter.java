package com.greenstreet.warehouse.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.greenstreet.warehouse.security.SecurityConstant.SECRET_KEY;
import static com.greenstreet.warehouse.security.SecurityConstant.TOKEN_PREFIX;
import static java.util.Arrays.stream;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public CustomAuthorizationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (!request.getServletPath().equals("/api/login")) {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header != null && header.startsWith(TOKEN_PREFIX)) {
                try {
                    String token = header.substring(TOKEN_PREFIX.length());
                    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    List<SimpleGrantedAuthority> authorities = stream(roles)
                            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                } catch (Exception e) {
                    log.error("Error Authorization! -> {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());

                    Map<String, String> error = new HashMap<>();
                    error.put("error_msg", e.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    objectMapper.writeValue(response.getOutputStream(), error);
                }
            }
        }
        chain.doFilter(request,response);
    }
}
