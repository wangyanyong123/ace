package com.github.wxiaoqi.security.app.vo.propertybill.in;

import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserPayBillVo implements Serializable {
    //用户缴费
    private static final long serialVersionUID = -5909270526402578580L;


    @ApiModelProperty(value = "缴费总金额")
    private String payAmount;
    @ApiModelProperty(value = "支付方式(支付宝、微信)")
    private String payType;
    @ApiModelProperty(value = "所属账期列表")
    private ShouldDateList shouldDateList;

}
