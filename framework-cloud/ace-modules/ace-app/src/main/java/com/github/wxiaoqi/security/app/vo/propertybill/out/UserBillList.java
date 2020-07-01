package com.github.wxiaoqi.security.app.vo.propertybill.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserBillList implements Serializable {
    private static final long serialVersionUID = 3407960022481258893L;

    @ApiModelProperty(value = "所属账期")
    private String shouldDate;
    @ApiModelProperty(value = "账单信息")
    private List<ShouldBillOut> shouldBillOut;
    @ApiModelProperty(value = "账单总金额")
    private String totalAmount;

}
