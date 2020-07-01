package com.github.wxiaoqi.security.app.vo.propertybill.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ShouldBillList implements Serializable {
    private static final long serialVersionUID = -7355924671544518628L;


    @ApiModelProperty(value = "所属账期")
    private String shouldDate;
    @ApiModelProperty(value = "收费科目")
    private String item;
    @ApiModelProperty(value = "应收金额")
    private String shouldAmount;
    @ApiModelProperty(value = "应收ID")
    private String shouldId;
    @ApiModelProperty(value = "收费金额")
    private String alreadyAmount;
    @ApiModelProperty(value = "收费状态")
    private String chargeStatus;
    @ApiModelProperty(value = "欠费金额")
    private String arrearsAmount;
    @ApiModelProperty(value = "实收金额")
    private String receivableAmount;
    @ApiModelProperty(value = "缴费状态")
    private String payStatus;
    @ApiModelProperty(value = "缴费日期")
    private String payDate;

    private String itemStr;

    public String getItemStr(){
        String itemStr = "";
        if("1".equals(item)){
            itemStr = "物业管理费";
        }else if("2".equals(item)){
            itemStr = "车位费";
        }
        return itemStr;
    }
}
