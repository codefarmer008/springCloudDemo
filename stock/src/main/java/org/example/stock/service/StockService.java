package org.example.stock.service;

public interface StockService {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
