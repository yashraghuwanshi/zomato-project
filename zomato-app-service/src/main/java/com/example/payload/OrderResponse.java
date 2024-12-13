package com.example.payload;

import java.util.Date;

public class OrderResponse {

    private String orderId;
    private String name;
    private int qty;
    private double price;
    private Date orderDate;
    private String status;
    private int estimateDeliveryWindow;

    public OrderResponse(String orderId, String name, int qty, double price, Date orderDate, String status, int estimateDeliveryWindow) {
        this.orderId = orderId;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.orderDate = orderDate;
        this.status = status;
        this.estimateDeliveryWindow = estimateDeliveryWindow;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public int getEstimateDeliveryWindow() {
        return estimateDeliveryWindow;
    }
}
