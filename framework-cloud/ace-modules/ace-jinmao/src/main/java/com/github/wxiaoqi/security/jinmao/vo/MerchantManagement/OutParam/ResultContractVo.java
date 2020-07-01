package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ResultContractVo implements Serializable {

    private String id;
    @ApiModelProperty(value = "协议ID")
    private String enterpriseId;
    @ApiModelProperty(value = "协议签署人")
    private String protocolPerson;
    @ApiModelProperty(value = "签署人手机")
    private String protocolTel;
    @ApiModelProperty(value = "签约日期")
    private Date signDate;
    @ApiModelProperty(value = "保证金")
    private BigDecimal bond;
    @ApiModelProperty(value = "年费")
    private BigDecimal annualFee;
    @ApiModelProperty(value = "协议文件URL")
    private String imageUrl;
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
    private List<ResultProjectInfo> projectId;
    @ApiModelProperty(value = "业务ID")
    private List<ResultBusinessInfo> busId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
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

//    public List<ImgInfo> getImageId() {
//        List<ImgInfo> list = new ArrayList<>();
//        if(StringUtils.isNotEmpty(imageUrl)){
//            String[] imArrayIds = new String[]{imageUrl};
//            if(imageUrl.indexOf(",")!= -1){
//                imArrayIds = imageUrl.split(",");
//            }
//            for (String url:imArrayIds){
//                ImgInfo info = new ImgInfo();
//                info.setUrl(url);
//                list.add(info);
//            }
//        }
//        return list;
//    }
//
//    public void setImageId(List<ImgInfo> imageId) {
//        this.imageId = imageId;
//    }

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

    public List<ResultProjectInfo> getProjectId() {
        return projectId;
    }

    public void setProjectId(List<ResultProjectInfo> projectId) {
        this.projectId = projectId;
    }

    public List<ResultBusinessInfo> getBusId() {
        return busId;
    }

    public void setBusId(List<ResultBusinessInfo> busId) {
        this.busId = busId;
    }
}
