package org.example.service;

import java.io.Serializable;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.po.Goods;
import org.example.mapper.GoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description  服务实现类
 *
 * @Author codefarmer008
 * @Date 2023-01-06 15:06
 */
@Service
@Slf4j
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> {


    /**
     * 按主键查询详情
     *
     * @param id 查询参数
     * @return
     */
    public Goods queryGoodsDetail(Serializable id){
        return this.getById(id);
    }


    /**
     * 添加
     *
     * @param goods 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addGoods(Goods goods){
        if(null == goods){
            return 0;
        }
        return this.save(goods)?1:0;
    }

    /**
     * 按主键修改
     *
     * @param goods 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer editGoods(Goods goods){
        if(null == goods || null == goods.getId()){
            throw new RuntimeException("缺少必填参数");
        }
        return this.updateById(goods)?1:0;
    }

    /**
     * 按主键进行逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteGoods(Serializable id){
        return this.removeById(id)?1:0;
    }
}
