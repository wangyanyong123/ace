package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultTenantManageInfo implements Serializable {

    @ApiModelProperty(value = "租户详情")
    private ResultTenantVo tenant;
    @ApiModelProperty(value = "支付宝账号详情")
    private ResultAilVo ali;
    @ApiModelProperty(value = "微信账号详情")
    private ResultWechatVo wechat;

    public ResultTenantVo getTenant() {
        return tenant;
    }

    public void setTenant(ResultTenantVo tenant) {
        this.tenant = tenant;
    }

    public ResultAilVo getAli() {
        return ali;
    }

    public void setAli(ResultAilVo ali) {
        this.ali = ali;
    }

    public ResultWechatVo getWechat() {
        return wechat;
    }

    public void setWechat(ResultWechatVo wechat) {
        this.wechat = wechat;
    }
}
