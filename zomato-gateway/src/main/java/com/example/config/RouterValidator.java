package com.example.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unused")
@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints= List.of(
            "/auth/signup",
            "/auth/login"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
