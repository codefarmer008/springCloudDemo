package org.example.elasticsearch.service;

import org.example.elasticsearch.entity.Goods;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GoodsService {
    boolean batchInsertGoods(List<Goods> goodsList) throws IOException;

    List<Map<String, Object>> queryGoodsByName(String name, int pageNo, int pageSize) throws IOException;
}
