package com.github.wxiaoqi.security.app.vo.shopping.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductInfo implements Serializable {

    @ApiModelProperty(value = "购物车id")
    private String id;
    @ApiModelProperty(value = "商品id")
    private String productId;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "购买数量")
    private String buyNum;
    @ApiModelProperty(value = "单价金额")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商品规格id")
    private String specId;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "商品规格图片")
    private String specImage;
    @ApiModelProperty(value = "商品规格图片地址")
    private List<ImgInfo> specImageList;
    @ApiModelProperty(value = "最小购买数量")
    private String lowestNum;
    @ApiModelProperty(value = "商品LOGO")
    private String productImage;
    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;

    @ApiModelProperty(value = "是否限制库存")
    private Boolean isLimit = true;

    @ApiModelProperty(value = "库存数量")
    private Integer stockNum = 0;

    public Boolean getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Boolean islimit) {
        this.isLimit = islimit;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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


    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getLowestNum() {
        return lowestNum;
    }

    public void setLowestNum(String lowestNum) {
        this.lowestNum = lowestNum;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecImage() {
        return specImage;
    }

    public void setSpecImage(String specImage) {
        this.specImage = specImage;
    }

    public List<ImgInfo> getSpecImageList() {
        return specImageList;
    }

    public void setSpecImageList(List<ImgInfo> specImageList) {
        this.specImageList = specImageList;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
