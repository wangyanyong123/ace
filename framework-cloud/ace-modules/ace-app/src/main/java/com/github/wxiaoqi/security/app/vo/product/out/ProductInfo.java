package com.github.wxiaoqi.security.app.vo.product.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.vo.productcomment.out.ProductCommentListVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductInfo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品LOGO")
    private String productImage;
    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "商品精选")
    private String selectionImage;
    @ApiModelProperty(value = "商品精选图片")
    private List<ImgInfo> selectionImageList;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "原价")
    private String originalPrice;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;
    @ApiModelProperty(value = "已选规格")
    private String selectedSpec;
    @ApiModelProperty(value = "送至地址")
    private String address;
    @ApiModelProperty(value = "购买数量")
    private String buyNum;
    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;

    @ApiModelProperty(value = "商品规格信息")
    private List<ProductSpecInfo> productSpecInfo;

    @ApiModelProperty(value = "公司id")
    private String companyId;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司logo")
    private String logoImg;
    @ApiModelProperty(value = "公司logo图片地址")
    private List<ImgInfo> logoImgList;

    //请使用commentList
    @Deprecated
    @ApiModelProperty(value = "用户评价")
    private List<UserCommentVo> userCommentList;

    @ApiModelProperty(value = "优惠券信息")
    private List<String> couponList;

    @ApiModelProperty(value = "是否支持发票(1-是2-否)")
    private String isInvoice;
    @ApiModelProperty(value = "是否打烊(1-是2-否)")
    private String isClose;


    @ApiModelProperty(value = "邮费信息")
    private List<PostageInfoVo> postageList;


    @ApiModelProperty(value = "当前库存数(-1表示无限制，已翻译)")
    private String stockNum;
    @ApiModelProperty(value = "是否有库存(1-有库存2-没有)")
    private String isStock;
    @ApiModelProperty(value = "用户是否达到购买上限(1-达到2-没有达到)")
    private String isBuy;

    private int productNum;
    private int limitNum;

    @ApiModelProperty(value = "商品评论列表-默认显示两条")
    private List<ProductCommentListVo> commentList;

    public List<ProductCommentListVo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ProductCommentListVo> commentList) {
        this.commentList = commentList;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public List<ImgInfo> getProductImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(productImage)){
            String[] imArrayIds = new String[]{productImage};
            if(productImage.contains(",")){
                imArrayIds = productImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public List<PostageInfoVo> getPostageList() {
        return postageList;
    }

    public void setPostageList(List<PostageInfoVo> postageList) {
        this.postageList = postageList;
    }

    public void setProductImageList(List<ImgInfo> productImageList) {
        this.productImageList = productImageList;
    }

    public String getSelectionImage() {
        return selectionImage;
    }

    public void setSelectionImage(String selectionImage) {
        this.selectionImage = selectionImage;
    }

    public List<ImgInfo> getSelectionImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(selectionImage)){
            String[] imArrayIds = new String[]{selectionImage};
            if(selectionImage.contains(",")){
                imArrayIds = selectionImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setSelectionImageList(List<ImgInfo> selectionImageList) {
        this.selectionImageList = selectionImageList;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getSelectedSpec() {
        return selectedSpec;
    }

    public void setSelectedSpec(String selectedSpec) {
        this.selectedSpec = selectedSpec;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public List<ProductSpecInfo> getProductSpecInfo() {
        return productSpecInfo;
    }

    public void setProductSpecInfo(List<ProductSpecInfo> productSpecInfo) {
        this.productSpecInfo = productSpecInfo;
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

    public List<UserCommentVo> getUserCommentList() {
        return userCommentList;
    }

    public void setUserCommentList(List<UserCommentVo> userCommentList) {
        this.userCommentList = userCommentList;
    }

    public String getProductImagetextInfo() {
        return productImagetextInfo;
    }

    public void setProductImagetextInfo(String productImagetextInfo) {
        this.productImagetextInfo = productImagetextInfo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public List<String> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<String> couponList) {
        this.couponList = couponList;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getIsStock() {
        return isStock;
    }

    public void setIsStock(String isStock) {
        this.isStock = isStock;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }
}
