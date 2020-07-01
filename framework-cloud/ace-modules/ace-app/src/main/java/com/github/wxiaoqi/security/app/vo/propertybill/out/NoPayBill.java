package com.github.wxiaoqi.security.app.vo.propertybill.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NoPayBill implements Serializable {
    private static final long serialVersionUID = -4223674068794922670L;

    @ApiModelProperty(value = "总金额")
    private String total;
    @ApiModelProperty(value = "缴费状态(1-已缴费2-未缴费)")
    private String payStatus;
    @ApiModelProperty(value = "用户账单")
    private List<UserBillList> billList;
}
