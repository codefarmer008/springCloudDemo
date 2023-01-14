package org.example.sharding;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

public class CatIdShardingAlgorithm implements StandardShardingAlgorithm<Comparable<?>> {

    @Getter
    @Setter
    private Properties props = new Properties();

    @Override
    public void init() {
    }

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<Comparable<?>> shardingValue) {
        Comparable<?> value = shardingValue.getValue();
        if(value == null){
            return "t_goods_4";
        }
        Integer catId = (Integer) value;
        if(1001 == catId){
            return "t_goods_1";
        }else if(1002 == catId){
            return "t_goods_2";
        }else if(1003 == catId){
            return "t_goods_3";
        }else {
            return "t_goods_4";
        }
    }

    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final RangeShardingValue<Comparable<?>> shardingValue) {
        throw new UnsupportedOperationException("catId sharding algorithm can not tackle with range query.");
    }


    @Override
    public String getType() {
        return "CATID";
    }
}
