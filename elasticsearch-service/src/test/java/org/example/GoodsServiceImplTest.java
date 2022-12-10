package org.example;

import org.example.elasticsearch.entity.Goods;
import org.example.elasticsearch.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceImplTest {
    @Autowired
    private GoodsService goodsService;

    @Test
    public void batchInsertGoodsTest() throws IOException {
        Goods goods1 = new Goods();
        goods1.setName("娃哈哈pro123");
        goods1.setPrice(new BigDecimal(5.12));
        goods1.setImg("http://dsfs.com/dsdf.png");
        goods1.setDesc("好喝又健康");

        Goods goods2 = new Goods();
        goods2.setName("健力宝pro123");
        goods2.setPrice(new BigDecimal(3.12));
        goods2.setImg("http://dsfs.com/sfd.png");
        goods2.setDesc("难喝又有害");
        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(goods1);
        goodsList.add(goods2);

        goodsService.batchInsertGoods(goodsList);
    }
}
