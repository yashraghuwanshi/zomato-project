package com.example.controller;

import com.example.payload.OrderResponse;
import com.example.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private RestaurantService service;

    @GetMapping("/orders/status/{orderId}")
    public OrderResponse getOrder(@PathVariable String orderId) {

        LOGGER.info("Requested Order ID: {}", orderId);

        OrderResponse orderResponse = service.getOrder(orderId);

        LOGGER.info("Order Response: {}", orderResponse);

        return orderResponse;
    }
}
