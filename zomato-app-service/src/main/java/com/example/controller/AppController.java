package com.example.controller;

import com.example.payload.OrderResponse;
import com.example.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zomato")
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppService appService;

    @GetMapping("/{orderId}")
    public ResponseEntity<?> checkOrderStatus(@PathVariable String orderId){

        LOGGER.info("Requested Order ID: {}", orderId);

        OrderResponse orderResponse = appService.fetchOrderStatus(orderId);

        LOGGER.info("Order Response: {}", orderResponse);

        return ResponseEntity.ok(orderResponse);
    }
}
