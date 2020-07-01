package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultWechatVo implements Serializable {

    @ApiModelProperty(value = "微信账号信息id")
    private String id;
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "微信支付appid")
    private String wechatAppid;

    @ApiModelProperty(value = "微信支付证书密码")
    private String wechatCode;

    @ApiModelProperty(value = "微信支付证书地址")
    private String wechatCertificate;

    @ApiModelProperty(value = "微信支付手续费")
    private Double wechatFee;

    @ApiModelProperty(value = "微信支付商户帐号")
    private String wechatAccount;

    @ApiModelProperty(value = "微信支付密钥")
    private String wechatKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWechatAppid() {
        return wechatAppid;
    }

    public void setWechatAppid(String wechatAppid) {
        this.wechatAppid = wechatAppid;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getWechatCertificate() {
        return wechatCertificate;
    }

    public void setWechatCertificate(String wechatCertificate) {
        this.wechatCertificate = wechatCertificate;
    }

    public Double getWechatFee() {
        return wechatFee;
    }

    public void setWechatFee(Double wechatFee) {
        this.wechatFee = wechatFee;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getWechatKey() {
        return wechatKey;
    }

    public void setWechatKey(String wechatKey) {
        this.wechatKey = wechatKey;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
