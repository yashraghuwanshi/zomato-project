package com.example.controller;

import com.example.payload.OrderResponse;
import com.example.service.AppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SuppressWarnings("all")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/zomato")
public class AppController {

    private final AppService appService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> checkOrderStatus(@PathVariable String orderId){

        log.info("Requested Order ID: {}", orderId);

        OrderResponse orderResponse = appService.fetchOrderStatus(orderId);

        log.info("Order Response: {}", orderResponse);

        return ResponseEntity.ok(orderResponse);
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(){
        List<OrderResponse> orders = appService.getOrders();
        return ResponseEntity.ok(orders);
    }
}
