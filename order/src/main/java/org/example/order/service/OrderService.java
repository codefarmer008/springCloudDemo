package org.example.order.service;


public interface OrderService {

    void placeOrder(String userId, String commodityCode, Integer count);
}
