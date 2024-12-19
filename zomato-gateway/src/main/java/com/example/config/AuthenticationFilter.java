package com.example.config;

import com.example.exceptions.AuthorizationHeaderException;
import com.example.exceptions.InvalidJwtException;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouterValidator routerValidator; // Custom route validator
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        super(Config.class);
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            if (routerValidator.isSecured.test(exchange.getRequest())) {

                // Check if the header contains the Authorization key
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new AuthorizationHeaderException("Authorization header is missing or invalid");
                }

                String token = authHeader.substring(7);

                // Validate the JWT token
                if (jwtUtil.isInvalid(token)) {
                    throw new InvalidJwtException("Authorization header is invalid");
                }

                Claims claims = jwtUtil.getAllClaimsFromToken(token);

                String roles = claims.get("roles").toString();

                if (!roles.contains("ROLE_USER") || roles.contains("ROLE_ADMIN")) {
                    var response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
        // Add config properties if needed
    }
}
