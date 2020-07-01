package com.github.wxiaoqi.security.jinmao.vo.statement.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BillInfo implements Serializable {

    @ApiModelProperty(value = "账单id")
    private String id;
    @ApiModelProperty(value = "营收金额")
    private String revenueMoney;
    @ApiModelProperty(value = "结算金额")
    private String balanceMoney;
    @ApiModelProperty(value = "结算状态(0：未结算，1：结算中，2：已结算)")
    private String balanceStatus;
    @ApiModelProperty(value = "商户账号")
    private String account;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;
    @ApiModelProperty(value = "联系人")
    private String contactorName;
    @ApiModelProperty(value = "联系电话")
    private String contactTel;
    @ApiModelProperty(value = "结算图")
    private String balanceImg;
    @ApiModelProperty(value = "结算图")
    private List<ImgInfo> balanceImgList;
    @ApiModelProperty(value = "商户id")
    private String tenantId;
    @ApiModelProperty(value = "是否打印(0-没有,1-有)")
    private String isPrint;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRevenueMoney() {
        return revenueMoney;
    }

    public void setRevenueMoney(String revenueMoney) {
        this.revenueMoney = revenueMoney;
    }

    public String getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(String balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(String balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getContactorName() {
        return contactorName;
    }

    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getBalanceImg() {
        return balanceImg;
    }

    public void setBalanceImg(String balanceImg) {
        this.balanceImg = balanceImg;
    }

    public List<ImgInfo> getBalanceImgList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(balanceImg)){
            String[] imArrayIds = new String[]{balanceImg};
            if(balanceImg.indexOf(",")!= -1){
                imArrayIds = balanceImg.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setBalanceImgList(List<ImgInfo> balanceImgList) {
        this.balanceImgList = balanceImgList;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }
}
