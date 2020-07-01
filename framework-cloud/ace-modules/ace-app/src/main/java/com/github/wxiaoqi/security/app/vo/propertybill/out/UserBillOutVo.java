package com.github.wxiaoqi.security.app.vo.propertybill.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserBillOutVo implements Serializable {


    private static final long serialVersionUID = -2102410273693864217L;
    @ApiModelProperty(value = "账单ID")
    private String subId;

    @ApiModelProperty(value = "账单标题")
    private String title;

    @ApiModelProperty(value = "实际支付ID")
    private String actualId;

    @ApiModelProperty(value = "实际支付金额(元)")
    private String actualPrice;

}
