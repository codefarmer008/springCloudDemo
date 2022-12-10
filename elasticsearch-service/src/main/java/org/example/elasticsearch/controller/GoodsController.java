package org.example.elasticsearch.controller;

import org.example.elasticsearch.entity.Goods;
import org.example.elasticsearch.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/addGoods")
    public String addGoods(String name, String img, BigDecimal price) {
        Goods goods = new Goods();
        goods.setName(name);
        goods.setImg(img);
        goods.setPrice(price);

        try {
            goodsService.batchInsertGoods(Collections.singletonList(goods));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("/queryGoodsByName")
    public List<Map<String, Object>> queryGoodsByName(String keywords) {

        try {
            List<Map<String, Object>> maps = goodsService.queryGoodsByName(keywords, 0 ,12);
            return maps;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
