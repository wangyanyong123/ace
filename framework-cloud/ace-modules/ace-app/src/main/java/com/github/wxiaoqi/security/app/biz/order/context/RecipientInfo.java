package com.github.wxiaoqi.security.app.biz.order.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收货人信息
 * @author: guohao
 * @create: 2020-04-18 12:27
 **/
@Data
public class RecipientInfo implements Serializable {

    private static final long serialVersionUID = -275753994689180443L;
    //联系人姓名
    @ApiModelProperty(value = "收货联系人姓名")
    private String contactName;

    //联系人手机号码
    @ApiModelProperty(value = "收货联系人手机号码")
    private String contactTel;

    //地址
    @ApiModelProperty(value = "收货地址")
    private String addr;

    @ApiModelProperty(value = "收货地址省编码")
    private String procCode;

    @ApiModelProperty(value = "收货地址城市编码")
    private String cityCode;

}
