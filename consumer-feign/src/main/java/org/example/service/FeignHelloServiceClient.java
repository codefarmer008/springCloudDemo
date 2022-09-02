package org.example.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hello-service",fallback = FeignHelloServiceImpl.class)
public interface FeignHelloServiceClient {

    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String hi(@RequestParam(name = "name") String name);
}
