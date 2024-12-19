package com.example.service;

import com.example.payload.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppService {

    private final RestClient.Builder restClientBuilder;

    public OrderResponse fetchOrderStatus(String orderId){

        log.info("Requested Order ID (APP SERVICE): {}", orderId);

        OrderResponse orderResponse = restClientBuilder
                .build()
                .get()
                .uri("http://restaurant-service/restaurant/orders/status/{orderId}", orderId)
                .retrieve()
                .toEntity(OrderResponse.class)
                .getBody();

        log.info("Order Response (APP SERVICE): {}", orderResponse);

        return orderResponse;
    }

    public List<OrderResponse> getOrders(){

        return restClientBuilder
                .build()
                .get()
                .uri("http://restaurant-service/restaurant/orders")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

    }
}
