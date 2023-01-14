package org.example.entity.po;
    
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 
 *
 * @Author codefarmer008
 * @Date 2023-01-06 15:06
 */
@Data
@TableName("t_goods")
public class Goods{

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "商品分类id")
    private Integer catId;
    @ApiModelProperty(value = "商品编码")
    private String goodsSn;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodsPrice;
    @ApiModelProperty(value = "店铺id")
    private Integer storeId;
    @ApiModelProperty(value = "上架时间")
    private Date onTime;
    @ApiModelProperty(value = "商品备注")
    private String goodsRemark;

}
