package com.github.wxiaoqi.security.app.vo.product.out;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class ProductSpecInfo implements Serializable {

    @ApiModelProperty(value = "规格id")
    private String id;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "最小购买数量")
    private String lowestNum;
    @ApiModelProperty(value = "商品规格图片")
    private String specImage;
    @ApiModelProperty(value = "商品图片")
    private String productImage;
    @ApiModelProperty(value = "商品图片地址")
    private List<ImgInfo> specImageList;
    @ApiModelProperty(value = "团购开始时间")
    private Date begTime;
    @ApiModelProperty(value = "团购结束时间")
    private Date endTime;
    @ApiModelProperty(value = "规格类型")
    private String specTypeCode;
    @ApiModelProperty(value = "计费类型值")
    private String specTypeVal;
    @ApiModelProperty(value = "原价")
    private String originalPrice;

    @ApiModelProperty(value = "是否限制库存")
    private Boolean isLimit = true;

    @ApiModelProperty(value = "库存数量")
    private Integer stockNum = 0;

//    @ApiModelProperty(value = "下午库存")
//    private Integer pmStockNum = 0;

    public List<ImgInfo> getSpecImageList() {
        if(CollectionUtils.isEmpty(specImageList) ){
            specImageList = new ArrayList<>();
            ImgInfo specImgInfo = new ImgInfo();
            if(StringUtils.isNotEmpty(specImage)){
                specImgInfo.setUrl(specImage);
            }else{
                specImgInfo.setUrl(productImage);
            }
            specImageList.add(specImgInfo);
        }
        return specImageList;
    }

//    @Override
//    public int compareTo(ProductSpecInfo o) {
//        if(o == null || stockNum == null || o.getStockNum() == null){
//            return 1;
//        }
//        if(!this.isLimit){
//            return -1;
//        }
//
//        if(!o.isLimit){
//            return 1;
//        }
//
//        if(stockNum > o.stockNum){
//            return -1;
//        }else if(stockNum == o.stockNum){
//            return 0;
//        }else{
//
//            return 1;
//        }
//    }
}
