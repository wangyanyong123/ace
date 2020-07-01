package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.postage.out.PostageInfoVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultMerchantVo implements Serializable {

    @ApiModelProperty(value = "商户ID")
    private String id;
    @ApiModelProperty(value = "商户名称")
    private String name;
    @ApiModelProperty(value = "营业执照号")
    private String licenceNo;
    @ApiModelProperty(value = "公司地址")
    private String address;
    @ApiModelProperty(value = "企业法人")
    private String juristicPerson;
    @ApiModelProperty(value = "成立时间")
    private Date setupTime;
    @ApiModelProperty(value = "注册资本,以万元为单位")
    private String regCapital;
    @ApiModelProperty(value = "负责人名字")
    private String contactorName;
    @ApiModelProperty(value = "负责人电话")
    private String contactTel;
    @ApiModelProperty(value = "负责人邮箱")
    private String contactEmail;
    @ApiModelProperty(value = "商户logo")
    private String logo;
    @ApiModelProperty(value = "logo地址")
    private List<ImgInfo> logoImg;
    @ApiModelProperty(value = "简介")
    private String summary;//简介
    @ApiModelProperty(value = "营业资历")
    private String qualificImg;
    @ApiModelProperty(value = "营业资历图片")
    private List<ImgInfo> qualificImgList;

    @ApiModelProperty(value = "是否可打烊")
    private String isClose;
    @ApiModelProperty(value = "是否可开票")
    private String isInvoice;

    @ApiModelProperty(value = "是否可打印")
    private String isPrint;

    @ApiModelProperty(value = "邮费规则")
    private List<PostageInfoVo> postageList;
    @ApiModelProperty(value = "免邮费规则")
    private String startAmount;
    private String endAmount;
    private String postage;
    @ApiModelProperty(value = "邮费ID")
    private String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setStartAmount(String startAmount) {
        this.startAmount = startAmount;
    }

    public void setEndAmount(String endAmount) {
        this.endAmount = endAmount;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getStartAmount() {
        if (startAmount!=null && startAmount.indexOf(".") > 0) {
            // 去掉多余的0
            startAmount = startAmount.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            startAmount = startAmount.replaceAll("[.]$", "");
        }
        return startAmount;
    }

    public String getEndAmount() {
        if ( endAmount!=null && endAmount.indexOf(".") > 0) {
            // 去掉多余的0
            endAmount = endAmount.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            endAmount = endAmount.replaceAll("[.]$", "");
        }
        return endAmount;
    }

    public String getPostage() {
        if (postage!=null && postage.indexOf(".") > 0) {
            // 去掉多余的0
            postage = postage.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            postage = postage.replaceAll("[.]$", "");
        }
        return postage;
    }

    public List<PostageInfoVo> getPostageList() {
        return postageList;
    }

    public void setPostageList(List<PostageInfoVo> postageList) {
        this.postageList = postageList;
    }

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }




    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJuristicPerson() {
        return juristicPerson;
    }

    public void setJuristicPerson(String juristicPerson) {
        this.juristicPerson = juristicPerson;
    }

    public Date getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(Date setupTime) {
        this.setupTime = setupTime;
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<ImgInfo> getLogoImg() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(logo)){
            String[] imArrayIds = new String[]{logo};
            if(logo.indexOf(",")!= -1){
                imArrayIds = logo.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setLogoImg(List<ImgInfo> logoImg) {
        this.logoImg = logoImg;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getQualificImg() {
        return qualificImg;
    }

    public void setQualificImg(String qualificImg) {
        this.qualificImg = qualificImg;
    }

    public List<ImgInfo> getQualificImgList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(qualificImg)){
            String[] imArrayIds = new String[]{qualificImg};
            if(qualificImg.indexOf(",")!= -1){
                imArrayIds = qualificImg.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setQualificImgList(List<ImgInfo> qualificImgList) {
        this.qualificImgList = qualificImgList;
    }
}
