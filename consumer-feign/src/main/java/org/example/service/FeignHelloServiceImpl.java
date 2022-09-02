package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class FeignHelloServiceImpl implements FeignHelloServiceClient{
    @Override
    public String hi(String name) {
        return "sorry "+ name;
    }
}
