package org.example.stock.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.stock.entity.Stock;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StockDAO extends BaseMapper<Stock> {

}