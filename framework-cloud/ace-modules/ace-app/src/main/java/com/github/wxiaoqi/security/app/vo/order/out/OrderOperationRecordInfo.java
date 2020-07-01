package com.github.wxiaoqi.security.app.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderOperationRecordInfo implements Serializable {
    private static final long serialVersionUID = -2581076527480066709L;


    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "父类Id")
    private String parentId;

    @ApiModelProperty(value = "当前步骤状态")
    private Integer stepStatus;

    @ApiModelProperty(value = "当前步骤")
    private String currStep;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
