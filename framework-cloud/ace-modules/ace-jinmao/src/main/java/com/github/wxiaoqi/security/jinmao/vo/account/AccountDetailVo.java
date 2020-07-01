package com.github.wxiaoqi.security.jinmao.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDetailVo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品编码")
    private String productCode;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "发布时间")
    private String publishTime;
    @ApiModelProperty(value = "当前状态")
    private String busStatus;
    @ApiModelProperty(value = "所属分类")
    private String classifyName;
    @ApiModelProperty(value = "所属商家")
    private String tenantName;
    @ApiModelProperty(value = "发布项目")
    private String projectName;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "销量")
    private int sales;
    @ApiModelProperty(value = "订单总金额")
    private String orderCost;

}
