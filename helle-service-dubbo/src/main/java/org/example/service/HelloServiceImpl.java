package org.example.service;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.example.mapper.UserMapper;
import org.example.service.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HelloServiceImpl implements HelloService {

//    @Autowired
//    private UserMapper userMapper;


    @Override
    @Transactional
    public String sayHello(String name) {
        System.out.println("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
//        User user = new User();
//        user.setId(2);
//        user.setName(name);
//        user.setPwd("123455");
//        userMapper.addUser(user);
        return "Hello " + name;
    }
}
