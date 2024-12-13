package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("zomato-app-service", r -> r.path("/zomato/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://zomato-app-service"))
                .route("restaurant-service", r -> r.path("/restaurant/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://restaurant-service"))
                .route("authentication-service", r -> r.path("/auth/**", "/api/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://authentication-service"))
                .build();
    }
}