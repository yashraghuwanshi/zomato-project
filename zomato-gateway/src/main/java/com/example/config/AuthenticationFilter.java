package com.example.config;

import com.example.exceptions.AuthorizationHeaderException;
import com.example.exceptions.InvalidJwtException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<Object> {

    private final RouterValidator routerValidator; // Custom route validator
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Object config) {

        return ((exchange, chain) -> {
            if (routerValidator.isSecured.test(exchange.getRequest())) {

                // Check if the header contains the Bearer Token
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new AuthorizationHeaderException("Authorization header is missing or invalid");
                }

                // Extract JWT from Authorization Header
                String token = authHeader.substring(7);

                // Validate the JWT
                if (jwtUtil.isInvalid(token)) {
                    throw new InvalidJwtException("Authorization header is invalid");
                }

                // Extract claims from the JWT
                Claims claims = jwtUtil.getAllClaimsFromToken(token);

                // Extract roles from the claims
                String roles = claims.get("roles").toString();

                log.debug("roles: {}", roles);

                // Add roles to a custom header (e.g., X-Roles)
                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("X-Roles", roles)
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            }
            return chain.filter(exchange);
        });
    }
}
