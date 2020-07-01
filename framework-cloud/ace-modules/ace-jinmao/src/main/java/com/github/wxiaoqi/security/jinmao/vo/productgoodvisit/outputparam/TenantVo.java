package com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TenantVo implements Serializable {

    @ApiModelProperty(value = "商户id")
    private String tenantId;

    @ApiModelProperty(value = "商户名称")
    private String tenantName;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
