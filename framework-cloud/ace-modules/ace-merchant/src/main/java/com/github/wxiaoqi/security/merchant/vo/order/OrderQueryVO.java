package com.github.wxiaoqi.security.merchant.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 我的商品订单查询
 */
@Data
public class OrderQueryVO {

    // 页码
    @ApiModelProperty(value = "页码")
    private int page;
    // 每页显示记录数
    @ApiModelProperty(value = "每页显示记录数")
    private int limit = 10;
    //订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30:待评价 ，35：已完成；40：退款中 ；45：已关闭；
    @ApiModelProperty(value = "订单状态(5：待支付，10：待发货，15：部分发货20：待签收, 30:待评价 ，35：已完成；40：退款中 ；45：已关闭）")
    private Integer orderStatus;

    @ApiModelProperty(value = "联系人电话/联系人姓名")
    private String keyword;

}
