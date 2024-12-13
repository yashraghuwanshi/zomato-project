package com.example.service;

import com.example.payload.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppService.class);

    @Autowired
    private RestClient.Builder restClientBuilder;

    public OrderResponse fetchOrderStatus(String orderId){

        LOGGER.info("Requested Order ID (APP SERVICE): {}", orderId);

        OrderResponse orderResponse = restClientBuilder
                .build()
                .get()
                .uri("http://ZOMATO-RESTAURANT-SERVICE/restaurant/orders/status/{orderId}", orderId)
                .retrieve()
                .toEntity(OrderResponse.class)
                .getBody();

        LOGGER.info("Order Response (APP SERVICE): {}", orderResponse);

        return orderResponse;
    }
}
