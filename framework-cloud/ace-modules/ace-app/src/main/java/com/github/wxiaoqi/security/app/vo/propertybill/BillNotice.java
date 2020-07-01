package com.github.wxiaoqi.security.app.vo.propertybill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BillNotice implements Serializable {
    private static final long serialVersionUID = 7719682251812273304L;


    @ApiModelProperty(value = "支付方式(1-支付宝2-微信)")
    private String payType;
    @ApiModelProperty(value = "账期信息")
    private List<ShouldDateList> shouldDateList;
    @ApiModelProperty(value = "支付金额")
    private String payAmount;
    @ApiModelProperty(value = "缴费时间")
    private String chargeTime;

}
