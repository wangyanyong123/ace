package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class MerchantSaveParam implements Serializable {

    private String id;
    @ApiModelProperty(value = "商户名称")
    private String name;
    @ApiModelProperty(value = "营业执照号")
    private String licenceNo;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "法人代表")
    private String juristicPerson;
    @ApiModelProperty(value = "成立时间")
    private String setupTime;
    @ApiModelProperty(value = "注册资本")
    private String regCapital;
    @ApiModelProperty(value = "联系人")
    private String contactorName;
    @ApiModelProperty(value = "联系方式")
    private String contactTel;
    @ApiModelProperty(value = "联系人邮箱")
    private String contactEmail;
    @ApiModelProperty(value = "商户logo")
    private List<ImgInfo> logoImg;
    @ApiModelProperty(value = "营业资历图片")
    private List<ImgInfo> qualificImgList;
    private String summary;//简介

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

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
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

    public List<ImgInfo> getLogoImg() {
        return logoImg;
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

    public List<ImgInfo> getQualificImgList() {
        return qualificImgList;
    }

    public void setQualificImgList(List<ImgInfo> qualificImgList) {
        this.qualificImgList = qualificImgList;
    }
}
