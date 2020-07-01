package com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultTenantInfo implements Serializable {


    @ApiModelProperty(value = "商户ID")
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
