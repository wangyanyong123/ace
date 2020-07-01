package com.github.wxiaoqi.security.jinmao.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class BalanceStatusInfo implements Serializable {

    @ApiModelProperty(value = "结算金额条形图")
    private List<Map<String,String>> settlementAccount;
    @ApiModelProperty(value = "结算状态占比饼图")
    private List<Map<String,String>> balanceStatusAccount;
}
