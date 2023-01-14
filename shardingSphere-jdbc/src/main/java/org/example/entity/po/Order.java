package org.example.entity.po;
    
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * @Description 
 *
 * @Author codefarmer008
 * @Date 2023-01-06 17:42
 */
@Data
@TableName("t_order")
public class Order {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "订单号")
    private Integer orderId;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;
    @ApiModelProperty(value = "订单备注")
    private String orderRemark;
    @ApiModelProperty(value = "收货地址 id")
    private String addressId;

}
