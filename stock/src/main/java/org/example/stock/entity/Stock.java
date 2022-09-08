package org.example.stock.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stock_tbl")
public class Stock {

    private Long id;
    private String commodityCode;
    private Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}