package com.github.wxiaoqi.security.app.vo.propertybill.in;

import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserBillOrder implements Serializable {
    private static final long serialVersionUID = -4731918247488443301L;

    @ApiModelProperty(value = "账单信息")
    private List<ShouldDateList> shouldBillOut;
    @ApiModelProperty(value = "所属账期")
    private String shouldDate;
    @ApiModelProperty(value = "总金额")
    private String totalAmount;
}
