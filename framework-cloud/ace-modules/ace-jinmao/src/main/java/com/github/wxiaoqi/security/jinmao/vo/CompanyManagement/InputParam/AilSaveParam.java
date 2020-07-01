package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AilSaveParam implements Serializable {

    private String id;
    @ApiModelProperty(value = "租户(公司、商户)ID")
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
    private String isPayInMall;


    private String appId;
    private String appRsa;
    private String appAliPublicKey;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAliPublicKey() {
        return aliPublicKey;
    }

    public void setAliPublicKey(String aliPublicKey) {
        this.aliPublicKey = aliPublicKey;
    }

    public String getIsPayInMall() {
        return isPayInMall;
    }

    public void setIsPayInMall(String isPayInMall) {
        this.isPayInMall = isPayInMall;
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
