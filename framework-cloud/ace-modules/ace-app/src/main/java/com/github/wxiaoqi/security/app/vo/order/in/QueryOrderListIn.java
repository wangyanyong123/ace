package com.github.wxiaoqi.security.app.vo.order.in;

import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author: guohao
 * @create: 2020-04-20 10:27
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryOrderListIn extends BaseQueryIn {
    private static final long serialVersionUID = -5890578836592819251L;

    @ApiModelProperty(value = "订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30待评价 35：已完成；40：退款中 ；45：已关闭；")
    private Integer orderStatus;

    @ApiModelProperty(value = "是否评论 0：未评论，1.已评论")
    private Integer commentStatus;

    @ApiModelProperty(value = "退款状态：0：无退款，10：退款中，15:部分退款 20：已退款")
    private Integer refundStatus;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @Override
    public void doCheck() {

    }
}
