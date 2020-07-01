package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ContractSaveParam implements Serializable {

    private String id;
    @ApiModelProperty(value = "商户ID")
    private String enterpriseId;
    @ApiModelProperty(value = "联系人")
    private String protocolPerson;
    @ApiModelProperty(value = "联系方式")
    private String protocolTel;
    @ApiModelProperty(value = "签约日期")
    private String signDate;
    @ApiModelProperty(value = "保证金")
    private BigDecimal bond;
    @ApiModelProperty(value = "年费")
    private BigDecimal annualFee;
    @ApiModelProperty(value = "协议文件")
    private List<ImgInfo> imageId;
    @ApiModelProperty(value = "收款账户类型")
    private String accountType;
    @ApiModelProperty(value = "支付宝账号名/微信账号名/银行卡账号名")
    private String accountName;
    @ApiModelProperty(value = "支付宝账号/微信账号/银行卡账号")
    private String accountNumber;
    @ApiModelProperty(value = "开户银行名称")
    private String accountBookName;
    @ApiModelProperty(value = "项目ID")
    private List<Map<String,String>> projectId;
    @ApiModelProperty(value = "业务ID")
    private List<Map<String,String>>  busId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getProtocolPerson() {
        return protocolPerson;
    }

    public void setProtocolPerson(String protocolPerson) {
        this.protocolPerson = protocolPerson;
    }

    public String getProtocolTel() {
        return protocolTel;
    }

    public void setProtocolTel(String protocolTel) {
        this.protocolTel = protocolTel;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public BigDecimal getBond() {
        return bond;
    }

    public void setBond(BigDecimal bond) {
        this.bond = bond;
    }

    public BigDecimal getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(BigDecimal annualFee) {
        this.annualFee = annualFee;
    }

    public List<ImgInfo> getImageId() {
        return imageId;
    }

    public void setImageId(List<ImgInfo> imageId) {
        this.imageId = imageId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountBookName() {
        return accountBookName;
    }

    public void setAccountBookName(String accountBookName) {
        this.accountBookName = accountBookName;
    }

    public void setProjectId(List<Map<String, String>> projectId) {
        this.projectId = projectId;
    }

    public void setBusId(List<Map<String, String>> busId) {
        this.busId = busId;
    }

    public List<Map<String, String>> getProjectId() {
        return projectId;
    }

    public List<Map<String, String>> getBusId() {
        return busId;
    }
}
