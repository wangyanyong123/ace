package com.github.wxiaoqi.security.jinmao.vo.statement.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BillDetail implements Serializable {

    @ApiModelProperty(value = "商户信息")
    private BillInfo billInfo;
    @ApiModelProperty(value = "账单明细列表")
    private List<BillDetailList> billDetailList;
    @ApiModelProperty(value = "账单明细列表数量")
    private int total;
}
