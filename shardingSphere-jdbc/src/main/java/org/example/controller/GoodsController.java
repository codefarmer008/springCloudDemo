package org.example.controller;

import org.example.entity.po.Goods;
import org.example.service.GoodsServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/goods")
@Slf4j
public class GoodsController {
        @Autowired
        private GoodsServiceImpl iGoodsService;


        @PostMapping("/queryGoodsDetail")
        public Goods queryGoodsDetail(Long id) {
            return iGoodsService.queryGoodsDetail(id);
        }


        @PostMapping("/addGoods")
        public String addGoods(@RequestBody @Validated Goods goods) {
            iGoodsService.addGoods(goods);
            return "成功";
        }

        @PostMapping("/editGoods")
        public String editGoods(@RequestBody @Validated Goods goods) {
            iGoodsService.editGoods(goods);
            return "成功";
        }

        @PostMapping("/deleteGoods")
        public String deleteGoods(Long id) {
            iGoodsService.deleteGoods(id);
            return "成功";
        }
}
