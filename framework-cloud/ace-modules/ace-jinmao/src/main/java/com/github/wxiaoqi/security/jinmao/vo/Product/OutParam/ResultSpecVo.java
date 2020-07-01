package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultSpecVo implements Serializable {

    @ApiModelProperty(value = "商品规格id")
    private String id;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "原价")
    private String originalPrice;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "最小量")
    private String lowestNum;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商品图片(不需要传)")
    private String specImage;
    @ApiModelProperty(value = "商品图片地址")
    private List<ImgInfo> specImageList;

    private String specTypeCode;

    @ApiModelProperty(value = "库存数量用于编辑时显示")
    private String storeNumDesc;

    @ApiModelProperty(value = "库存数量")
    private Integer storeNum;

    @ApiModelProperty(value = "预约服务下午库存")
    private Integer storeNumAfternoon;

    @ApiModelProperty(value = "预约服务上午库存")
    private Integer storeNumForenoon;

    public Integer getStoreNumAfternoon() {
        return storeNumAfternoon;
    }

    public void setStoreNumAfternoon(Integer storeNumAfternoon) {
        this.storeNumAfternoon = storeNumAfternoon;
    }

    public Integer getStoreNumForenoon() {
        return storeNumForenoon;
    }

    public void setStoreNumForenoon(Integer storeNumForenoon) {
        this.storeNumForenoon = storeNumForenoon;
    }

    public String getStoreNumDesc() {
        return storeNumDesc;
    }

    public void setStoreNumDesc(String storeNumDesc) {
        this.storeNumDesc = storeNumDesc;
    }

    public Integer getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLowestNum() {
        return lowestNum;
    }

    public void setLowestNum(String lowestNum) {
        this.lowestNum = lowestNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecImage() {
        return specImage;
    }

    public void setSpecImage(String specImage) {
        this.specImage = specImage;
    }

    public List<ImgInfo> getSpecImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(specImage)){
            String[] imArrayIds = new String[]{specImage};
            if(specImage.indexOf(",")!= -1){
                imArrayIds = specImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setSpecImageList(List<ImgInfo> specImageList) {
        this.specImageList = specImageList;
    }


    public String getSpecTypeCode() {
        return specTypeCode;
    }

    public void setSpecTypeCode(String specTypeCode) {
        this.specTypeCode = specTypeCode;
    }
}
