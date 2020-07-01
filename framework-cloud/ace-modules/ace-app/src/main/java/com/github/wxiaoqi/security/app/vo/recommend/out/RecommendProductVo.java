package com.github.wxiaoqi.security.app.vo.recommend.out;

import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecommendProductVo implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品简介")
    private String labelName;
    @ApiModelProperty(value = "商品图片")
    private String productImage;
    @ApiModelProperty(value = "商品图片地址")
    private List<ImgeInfo> productImageUrl;
    @ApiModelProperty(value = "商品价格")
    private String price;
    @ApiModelProperty(value = "商品单位")
    private String unit;
    @ApiModelProperty(value = "1-优选商品，2-预约商品")
    private String productType;

    @ApiModelProperty(value = "推荐图片")
    private String recommendImgUrl;

    @ApiModelProperty(value = "业务类型(1-普通类型，2-团购，3-好物探访，4-疯抢)")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecommendImgUrl() {
        return recommendImgUrl;
    }

    public void setRecommendImgUrl(String recommendImgUrl) {
        this.recommendImgUrl = recommendImgUrl;
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

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public List<ImgeInfo> getProductImageUrl() {
        List<ImgeInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(productImage)){
            String[] imArrayIds = new String[]{productImage};
            if(productImage.indexOf(",")!= -1){
                imArrayIds = productImage.split(",");
            }
            for (String url:imArrayIds){
                ImgeInfo info = new ImgeInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setProductImageUrl(List<ImgeInfo> productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
