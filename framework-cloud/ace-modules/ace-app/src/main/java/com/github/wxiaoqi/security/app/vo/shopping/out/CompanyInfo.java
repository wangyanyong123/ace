package com.github.wxiaoqi.security.app.vo.shopping.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyInfo implements Serializable {

    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "公司id")
    private String companyId;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司logo")
    private String logoImg;
    @ApiModelProperty(value = "公司logo图片地址")
    private List<ImgInfo> logoImgList;
    @ApiModelProperty(value = "是否支持发票(1-是2-否)")
    private String isInvoice;
    @ApiModelProperty(value = "是否打烊(1-是2-否)")
    private String isClose;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public List<ImgInfo> getLogoImgList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(logoImg)){
            String[] imArrayIds = new String[]{logoImg};
            if(logoImg.indexOf(",")!= -1){
                imArrayIds = logoImg.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setLogoImgList(List<ImgInfo> logoImgList) {
        this.logoImgList = logoImgList;
    }


    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }
}
