package org.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.po.Goods;
import org.springframework.stereotype.Repository;

/**
 * @Description  Mapper 接口
 *
 * @Author codefarmer008
 * @Date 2023-01-06 14:28
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}
