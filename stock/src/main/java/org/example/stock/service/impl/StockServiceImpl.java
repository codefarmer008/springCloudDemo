package org.example.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.stock.dao.StockDAO;
import org.example.stock.entity.Stock;
import org.example.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockDAO stockDAO;

    /**
     * 减库存
     *
     * @param commodityCode
     * @param count
     */
    @Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int count) {
        if (commodityCode.equals("product-2")) {
            throw new RuntimeException("异常:模拟业务异常:stock branch exception");
        }

        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        Stock stock1 = new Stock();
        stock1.setCommodityCode(commodityCode);
        wrapper.setEntity(stock1);
        Stock stock = stockDAO.selectOne(wrapper);
        stock.setCount(stock.getCount() - count);

        stockDAO.updateById(stock);
    }
}
