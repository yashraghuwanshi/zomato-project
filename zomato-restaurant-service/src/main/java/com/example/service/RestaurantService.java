package com.example.service;

import com.example.payload.OrderResponse;
import com.example.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public OrderResponse getOrder(String orderId){

        OrderResponse orderResponse = restaurantRepository.getOrders(orderId);
        log.info("Order Response: {}", orderResponse);
        return orderResponse;
    }

    public List<OrderResponse> getOrders(){
        return  restaurantRepository.getOrders();
    }
}
