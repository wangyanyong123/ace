package com.github.wxiaoqi.security.app.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubContactVo implements Serializable {
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "收货地址")
    private String deliveryAddr;
    @ApiModelProperty(value = "收货人电话")
    private String contactTel;
    @ApiModelProperty(value = "收货人姓名")
    private String contactName;
}
