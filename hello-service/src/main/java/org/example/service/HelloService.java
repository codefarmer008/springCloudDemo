package org.example.service;

import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;


@RestController
public class HelloService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Value("${deploy.env}")
    String env;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "prod") String name) throws UnknownHostException {
        logger.info("===== hello-service/hi ============"+env);
//        User user = new User();
//        user.setId(2);
//        user.setName("wuzb");
//        user.setPwd("123455");
//        userMapper.addUser(user);
        return "hi " + name + " ,i am at env:" + env+", ip:"+ InetAddress.getLocalHost().getHostAddress();
    }
}
