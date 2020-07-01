package com.github.wxiaoqi.security.app.vo.product.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.vo.productcomment.out.ProductCommentListVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupProductInfo implements Serializable {

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
    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;
    @ApiModelProperty(value = "已选规格")
    private String selectedSpec;
    @ApiModelProperty(value = "送至地址")
    private String address;
    @ApiModelProperty(value = "成团份数")
    private String groupbuyNum;
    @ApiModelProperty(value = "成团截止时间")
    private String endTime;
    @ApiModelProperty(value = "成团开始时间")
    private String begTime;
    @ApiModelProperty(value = "参团人数")
    private String sales;
    @ApiModelProperty(value = "商品状态(1-未开始,2-进行中,3-已过期,4-已告罄)")
    private String groupStatus;

    @ApiModelProperty(value = "疯抢商品总数")
    private String productNum;
    @ApiModelProperty(value = "已购商品数量")
    private int buyNum;

    @ApiModelProperty(value = "参团人头像图片地址(从这list获取参团人头像)")
    private List<ImgInfo> imgList;

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

    @ApiModelProperty(value = "是否支持发票(1-是2-否)")
    private String isInvoice;

    @ApiModelProperty(value = "疯抢当前时间")
    private String currentTime;

    @ApiModelProperty(value = "评论列表")
    private List<ProductCommentListVo> commentList;

    @ApiModelProperty(value = "服务器时间")
    public Date getServiceTime(){
        return new Date();
    }

    public List<ProductCommentListVo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ProductCommentListVo> commentList) {
        this.commentList = commentList;
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
            if(productImage.indexOf(",")!= -1){
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
            if(selectionImage.indexOf(",")!= -1){
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

    public String getProductImagetextInfo() {
        return productImagetextInfo;
    }

    public void setProductImagetextInfo(String productImagetextInfo) {
        this.productImagetextInfo = productImagetextInfo;
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

    public List<ProductSpecInfo> getProductSpecInfo() {
        return productSpecInfo;
    }

    public void setProductSpecInfo(List<ProductSpecInfo> productSpecInfo) {
        this.productSpecInfo = productSpecInfo;
    }

    public String getGroupbuyNum() {
        return groupbuyNum;
    }

    public void setGroupbuyNum(String groupbuyNum) {
        this.groupbuyNum = groupbuyNum;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public List<ImgInfo> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgInfo> imgList) {
        this.imgList = imgList;
    }

    public List<UserCommentVo> getUserCommentList() {
        return userCommentList;
    }

    public void setUserCommentList(List<UserCommentVo> userCommentList) {
        this.userCommentList = userCommentList;
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

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
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
            if(logoImg.contains(",")){
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

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
