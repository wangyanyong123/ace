package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultMerchantManageInfo implements Serializable {

    @ApiModelProperty(value = "商户详情")
    private ResultMerchantVo merchant;
    @ApiModelProperty(value = "协议详情")
    private ResultContractVo contract;

    public ResultMerchantVo getMerchant() {
        return merchant;
    }

    public void setMerchant(ResultMerchantVo merchant) {
        this.merchant = merchant;
    }

    public ResultContractVo getContract() {
        return contract;
    }

    public void setContract(ResultContractVo contract) {
        this.contract = contract;
    }
}
