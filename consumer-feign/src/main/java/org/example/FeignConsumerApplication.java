package org.example;

import com.netflix.loadbalancer.IRule;
import org.example.rule.EnvLoadbalancerRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@RibbonClients(defaultConfiguration = FeignConsumerApplication.class)
public class FeignConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApplication.class, args);
    }
    @Bean
    public IRule myRule(){
        //需要什么算法就new对应的类
        return new EnvLoadbalancerRule();
    }
}
