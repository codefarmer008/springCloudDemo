package org.example.order.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.example.order.dao.OrderDAO;
import org.example.order.feign.StockFeignClient;
import org.example.order.po.Order;
import org.example.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

//    @Resource
//    private AccountFeignClient accountFeignClient;
    @Resource
    private StockFeignClient stockFeignClient;
    @Resource
    private OrderDAO orderDAO;

    /**
     * 下单：创建订单、减库存，涉及到两个服务
     *
     * @param userId
     * @param commodityCode
     * @param count
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(String userId, String commodityCode, Integer count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(orderMoney);
        orderDAO.insert(order);
        stockFeignClient.deduct(commodityCode, count);

    }

//    @Transactional(rollbackFor = Exception.class)
//    public void create(String userId, String commodityCode, int count) {
//
//        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
//
//        Order order = new Order();
//        order.setUserId(userId);
//        order.setCommodityCode(commodityCode);
//        order.setCount(count);
//        order.setMoney(orderMoney);
//        orderDAO.insert(order);
//
//        accountFeignClient.reduce(userId, orderMoney);
//
//    }

}
