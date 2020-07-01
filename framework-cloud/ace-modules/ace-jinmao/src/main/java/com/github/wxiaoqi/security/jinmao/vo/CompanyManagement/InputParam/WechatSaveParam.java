package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam;

import java.io.Serializable;

public class WechatSaveParam implements Serializable {

    private String id;
    private String tenantId;
    private String wechatAppid;
    private String wechatCode;
    private String wechatCertificate;
    private String wechatFee;
    private String wechatAccount;
    private String wechatKey;
    private String isPayInMall;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getWechatFee() {
        return wechatFee;
    }

    public void setWechatFee(String wechatFee) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsPayInMall() {
        return isPayInMall;
    }

    public void setIsPayInMall(String isPayInMall) {
        this.isPayInMall = isPayInMall;
    }
}
