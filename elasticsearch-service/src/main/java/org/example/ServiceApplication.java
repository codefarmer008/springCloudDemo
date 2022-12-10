package org.example;

import org.example.web.cors.CorsFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {
    //跨域配置
    @Bean
    public FilterRegistrationBean corsFilterRegistrationBean() {
        return new CorsFilterFactory(null, false).getObject();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
