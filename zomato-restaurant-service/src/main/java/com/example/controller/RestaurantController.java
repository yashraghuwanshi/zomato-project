package com.example.controller;

import com.example.payload.OrderResponse;
import com.example.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService service;

    @GetMapping("/orders/status/{orderId}")
    public OrderResponse getOrder(@PathVariable String orderId) {

        log.info("Requested Order ID: {}", orderId);
        OrderResponse orderResponse = service.getOrder(orderId);
        log.info("Order Response: {}", orderResponse);
        return orderResponse;
    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrders() {
          return service.getOrders();
    }


}
