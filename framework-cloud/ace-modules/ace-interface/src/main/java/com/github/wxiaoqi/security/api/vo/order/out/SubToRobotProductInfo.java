package com.github.wxiaoqi.security.api.vo.order.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订购产品
 * @author huangxl
 * @date 2018-12-18
 */
@Data
public class SubToRobotProductInfo implements Serializable{

    @ApiModelProperty(value = "产品ID")
    private String productId;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "规格ID")
    private String specId;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "数量")
    private int subNum;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;
    //图片id,多张图片逗号分隔
    private String imgId;

}