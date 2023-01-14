package org.example.service;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.po.Order;
import org.example.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description  服务实现类
 *
 * @Author codefarmer008
 * @Date 2023-01-06 17:42
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> {



    /**
     * 按主键查询详情
     *
     * @param id 查询参数
     * @return
     */
    public Order queryOrderDetail(Serializable id){
        return this.getById(id);
    }


    /**
     * 添加
     *
     * @param order 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrder(Order order){
        if(null == order){
            return 0;
        }
        return this.save(order)?1:0;
    }

    /**
     * 按主键修改
     *
     * @param order 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer editOrder(Order order){
        if(null == order || null == order.getId()){
            throw new RuntimeException("缺少必填参数");
        }

        return this.updateById(order)?1:0;
    }

    /**
     * 按主键进行逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteOrder(Serializable id){
        return this.removeById(id)?1:0;
    }
}
