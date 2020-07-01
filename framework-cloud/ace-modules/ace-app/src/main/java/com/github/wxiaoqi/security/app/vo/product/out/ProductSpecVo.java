package com.github.wxiaoqi.security.app.vo.product.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecVo implements Serializable {


    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品LOGO")
    private String productImage;
    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;
    @ApiModelProperty(value = "商品规格信息")
    private List<ProductSpecInfo> productSpecInfo;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<ProductSpecInfo> getProductSpecInfo() {
        return productSpecInfo;
    }

    public void setProductSpecInfo(List<ProductSpecInfo> productSpecInfo) {
        this.productSpecInfo = productSpecInfo;
    }
}
