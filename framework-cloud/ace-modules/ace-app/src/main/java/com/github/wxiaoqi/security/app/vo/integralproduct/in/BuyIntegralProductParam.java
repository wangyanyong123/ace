package com.github.wxiaoqi.security.app.vo.integralproduct.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author qs
 * @date 2019/9/2
 */
@Data
public class BuyIntegralProductParam implements Serializable {

    //联系人姓名
    @ApiModelProperty(value = "收货联系人姓名")
    private String contactName;

    //联系人手机号码
    @ApiModelProperty(value = "收货联系人手机号码")
    private String contactTel;

    //地址
    @ApiModelProperty(value = "收货地址")
    private String addr;

    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "店铺备注")
    private String remark;

    @ApiModelProperty(value = "商品兑换积分")
    private String integral;

    @ApiModelProperty(value = "商品ID")
    private String productId;




}
