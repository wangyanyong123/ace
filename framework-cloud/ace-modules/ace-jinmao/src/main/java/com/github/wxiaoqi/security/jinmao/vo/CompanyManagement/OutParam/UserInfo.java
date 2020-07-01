package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UserInfo implements Serializable {

    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "类型(1-公司,2-商户,3-系统平台)")
    private String tenantType;
    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenantType() {
        return tenantType;
    }

    public void setTenantType(String tenantType) {
        this.tenantType = tenantType;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
