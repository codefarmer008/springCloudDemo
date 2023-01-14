package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.po.Order;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description  Mapper 接口
 *
 * @Author codefarmer008
 * @Date 2023-01-06 17:42
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
