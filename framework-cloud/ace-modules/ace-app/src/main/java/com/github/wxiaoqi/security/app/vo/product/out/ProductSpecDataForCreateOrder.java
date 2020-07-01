package com.github.wxiaoqi.security.app.vo.product.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: guohao
 * @create: 2020-04-19 11:12
 **/
@Data
public class ProductSpecDataForCreateOrder {

    @ApiModelProperty(value = "商户id")
    private String tenantId;

    @ApiModelProperty(value = "商品所属一级分类")
    private String busId;

    @ApiModelProperty(value = "商品所属一级分类")
    private String busName;

    @ApiModelProperty(value = "商品id")
    private String productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品图片")
    private String productImage;

    @ApiModelProperty(value = "业务状态(1-待发布，2-待审核，3-已发布，4已驳回，5-已下架）")
    private String busStatus;

    @ApiModelProperty(value = "商品规格id")
    private String specId;

    @ApiModelProperty(value = "商品规格名称")
    private String specName;

    @ApiModelProperty(value = "商品规格图片")
    private String specImage;

    @ApiModelProperty(value = "商品规格销售价")
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "商品最小购买数量")
    private BigDecimal lowestNum;

    @ApiModelProperty(value = "团购开始时间")
    private Date begTime;

    @ApiModelProperty(value = "团购结束时间")
    private Date endTime;

    @ApiModelProperty(value = "商品所属项目")
    private List<String> projectIdList;




}
