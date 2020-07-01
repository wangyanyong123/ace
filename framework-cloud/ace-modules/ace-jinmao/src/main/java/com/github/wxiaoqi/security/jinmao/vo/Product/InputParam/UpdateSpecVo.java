package com.github.wxiaoqi.security.jinmao.vo.Product.InputParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateSpecVo implements Serializable {

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
    @ApiModelProperty(value = "商品图片地址")
    private List<ImgInfo> specImageList;
    @ApiModelProperty(value = "规格类型")
    private String specTypeCode;

    @ApiModelProperty(value = "库存")
    private Integer storeNum;

    @ApiModelProperty(value = "预约服务下午库存")
    private Integer storeNumAfternoon;

    @ApiModelProperty(value = "预约服务上午库存")
    private Integer storeNumForenoon;
}
