package com.github.wxiaoqi.security.app.vo.shopping.out;

import com.github.wxiaoqi.security.app.vo.product.out.PostageInfoVo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ShoppingVo implements Serializable {

    @ApiModelProperty(value = "公司id")
    private String companyId;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司所属商品信息")
    private List<ProductInfo> productInfo;
    @ApiModelProperty(value = "是否支持发票(1-是2-否)")
    private String isInvoice;
    @ApiModelProperty(value = "邮费信息")
    private List<PostageInfoVo> postageList;
    @ApiModelProperty(value = "优惠券信息")
    private List<String> couponList;

    public List<String> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<String> couponList) {
        this.couponList = couponList;
    }

    public List<PostageInfoVo> getPostageList() {
        return postageList;
    }

    public void setPostageList(List<PostageInfoVo> postageList) {
        this.postageList = postageList;
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

    public List<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }
}
