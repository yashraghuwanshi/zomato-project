package com.example.service;

import com.example.payload.OrderResponse;
import com.example.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    public OrderResponse getOrder(String orderId){

        OrderResponse orderResponse = restaurantRepository.getOrders(orderId);

        LOGGER.info("Order Response: {}", orderResponse);

        return orderResponse;

    }
}
