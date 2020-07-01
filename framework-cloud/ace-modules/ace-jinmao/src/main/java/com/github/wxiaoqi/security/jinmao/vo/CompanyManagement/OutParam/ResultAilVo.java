package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAilVo implements Serializable {

    @ApiModelProperty(value = "支付宝账号信息id")
    private String id;
    @ApiModelProperty(value = "租户id")
    private String tenantId;
    @ApiModelProperty(value = "支付宝账号")
    private String alipayAccountNo;
    @ApiModelProperty(value = "支付宝商户名")
    private String alipayAccountName;
    @ApiModelProperty(value = "支付宝伙伴ID")
    private String alipayPartner;
    @ApiModelProperty(value = "支付宝Key")
    private String alipayKey;
    @ApiModelProperty(value = "支付宝rsa私钥内容")
    private String rsa;
    @ApiModelProperty(value = "支付宝rsa公钥内容")
    private String aliPublicKey;

    private String appId;
    private String appRsa;
    private String appAliPublicKey;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlipayAccountNo() {
        return alipayAccountNo;
    }

    public void setAlipayAccountNo(String alipayAccountNo) {
        this.alipayAccountNo = alipayAccountNo;
    }

    public String getAlipayAccountName() {
        return alipayAccountName;
    }

    public void setAlipayAccountName(String alipayAccountName) {
        this.alipayAccountName = alipayAccountName;
    }

    public String getAlipayPartner() {
        return alipayPartner;
    }

    public void setAlipayPartner(String alipayPartner) {
        this.alipayPartner = alipayPartner;
    }

    public String getAlipayKey() {
        return alipayKey;
    }

    public void setAlipayKey(String alipayKey) {
        this.alipayKey = alipayKey;
    }

    public String getRsa() {
        return rsa;
    }

    public void setRsa(String rsa) {
        this.rsa = rsa;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAliPublicKey() {
        return aliPublicKey;
    }

    public void setAliPublicKey(String aliPublicKey) {
        this.aliPublicKey = aliPublicKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppRsa() {
        return appRsa;
    }

    public void setAppRsa(String appRsa) {
        this.appRsa = appRsa;
    }

    public String getAppAliPublicKey() {
        return appAliPublicKey;
    }

    public void setAppAliPublicKey(String appAliPublicKey) {
        this.appAliPublicKey = appAliPublicKey;
    }
}
