package com.github.wxiaoqi.security.app.vo.propertybill.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InvoiceBillVo implements Serializable {

    @ApiModelProperty(value = "账单id")
    private String id;
    @ApiModelProperty(value = "缴费单编码")
    private String subCode;
    @ApiModelProperty(value = "支付时间")
    private String payDate;
    @ApiModelProperty(value = "实际总金额")
    private BigDecimal actualCost;
    @ApiModelProperty(value = "实际总金额字符串")
    private String actualCostStr;

    @ApiModelProperty(value = "是否开发票(0-不开发票,1-个人,2-公司)")
    private String invoiceType;


    public String getActualCostStr(){
        if(actualCost!=null){
            return (actualCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }else{
            return (new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }

    }
}
