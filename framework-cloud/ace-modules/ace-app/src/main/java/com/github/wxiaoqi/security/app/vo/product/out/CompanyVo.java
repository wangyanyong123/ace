package com.github.wxiaoqi.security.app.vo.product.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyVo implements Serializable {

    @ApiModelProperty(value = "商家名称")
    private String name;
    @ApiModelProperty(value = "商家地址")
    private String address;
    @ApiModelProperty(value = "商家电话")
    private String contactTel;
    @ApiModelProperty(value = "简介")
    private String summary;
    @ApiModelProperty(value = "营业资历图片")
    private String qualificImg;
    @ApiModelProperty(value = "营业资历图片地址")
    private List<ImgInfo> qualificImgList;
    @ApiModelProperty(value = "是否支持发票(1-是2-否)")
    private String isInvoice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
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
            if(qualificImg.contains(",")){
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

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }
}
