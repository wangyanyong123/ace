package com.github.wxiaoqi.security.app.vo.propertybill.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BillListOut implements Serializable {

    private static final long serialVersionUID = 801978349749611449L;
    @ApiModelProperty(value = "所属年份")
    private String year;
    @ApiModelProperty(value = "账单信息")
    private List<UserAllBillList> billList;

}
