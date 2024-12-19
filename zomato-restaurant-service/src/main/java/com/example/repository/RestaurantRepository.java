package com.example.repository;

import com.example.payload.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RestaurantRepository {

    public OrderResponse getOrders(String orderId) {
        return generateRandomOrders().get(orderId);
    }

    public List<OrderResponse> getOrders(){
        return new ArrayList<>(generateRandomOrders().values());
    }

    private Map<String, OrderResponse> generateRandomOrders() {
        Map<String, OrderResponse> orderMap = new HashMap<>();
        orderMap.put("35fds631", new OrderResponse("35fds63", "VEG-MEALS", 1, 199, new Date(), "READY", 15));
        orderMap.put("9u71245h", new OrderResponse("9u71245h", "HYDERABADI DUM BIRYANI", 2, 641, new Date(), "PREPARING", 59));
        orderMap.put("37jbd832", new OrderResponse("37jbd832", "PANEER BUTTER MASALA", 1, 325, new Date(), "DELIVERED", 0));
        return orderMap;
    }
}
