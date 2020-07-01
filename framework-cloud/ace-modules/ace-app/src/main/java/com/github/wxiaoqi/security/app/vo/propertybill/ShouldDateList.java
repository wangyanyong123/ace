package com.github.wxiaoqi.security.app.vo.propertybill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ShouldDateList implements Serializable {

    private static final long serialVersionUID = 6603564649863753840L;

    @ApiModelProperty(value = "收费科目(1-物业管理费,2-车位费)")
    private String item;
    @ApiModelProperty(value = "所属账期")
    private String shouldDate;
    @ApiModelProperty(value = "账期ID")
    private String shouldId;
    @ApiModelProperty(value = "应收金额")
    private String shouldAmount;

    private String year;

    private String mouth;

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
