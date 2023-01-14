package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.po.Order;
import org.example.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  控制器
 * </p>
 *
 * @author codefarmer008
 * @date 2023-01-06 14:28
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
        @Autowired
        private OrderServiceImpl orderService;


        @PostMapping("/queryOrderDetail")
        public Order queryOrderDetail(Long id) {
            return orderService.queryOrderDetail(id);
        }


        @PostMapping("/addOrder")
        public String addOrder(@RequestBody @Validated Order order) {
            orderService.addOrder(order);
            return "成功";
        }

        @PostMapping("/editOrder")
        public String editOrder(@RequestBody @Validated Order order) {
            orderService.editOrder(order);
            return "成功";
        }

        @PostMapping("/deleteOrder")
        public String deleteOrder(Long id) {
            orderService.deleteOrder(id);
            return "成功";
        }
}
