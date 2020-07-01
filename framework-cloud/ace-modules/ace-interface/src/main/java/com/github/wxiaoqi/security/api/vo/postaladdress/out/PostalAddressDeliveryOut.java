package com.github.wxiaoqi.security.api.vo.postaladdress.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PostalAddressDeliveryOut implements Serializable {

    @ApiModelProperty(value = "商户ID")
    private String tenantId;
    @ApiModelProperty(value = "区域编码")
    private String procCode;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }
}
