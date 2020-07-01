package com.github.wxiaoqi.security.jinmao.vo.Product.InputParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultClassifyVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultSpike;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateProductInfoVo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "业务id")
    private String busId;
    @ApiModelProperty(value = "业务名称(不需要传)")
    private String busName;
    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "商品精选图片")
    private List<ImgInfo> selectionImageList;
    @ApiModelProperty(value = "商品简介")
    private String productSummary;
    @ApiModelProperty(value = "商品售后")
    private String productAfterSale;
    @ApiModelProperty(value = "图文详情")
    private String productImagetextInfo;
//    @ApiModelProperty(value = "团购总份数")
//    private String productNum;
    @ApiModelProperty(value = "团购成团数")
    private String groupbuyNum;
    @ApiModelProperty(value = "团购开始时间 ")
    private String begTime;
    @ApiModelProperty(value = "团购结束时间 ")
    private String endTime;
    @ApiModelProperty(value = "项目范围")
    private List<ResultProjectVo> projectVo;
    @ApiModelProperty(value = "商品分类 ")
    private List<ResultClassifyVo> classifyVo;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;
//    @ApiModelProperty(value = "秒杀商品 ")
//    private List<ResultSpike> spikeArr;
    @ApiModelProperty(value = "单账号限制购买数量")
    private String limitNum;
    @ApiModelProperty(value = "商品库存")
    private String productNums;
    @ApiModelProperty(value = "供应商")
    private String supplier;
    @ApiModelProperty(value = "销售方式")
    private String salesWay;

}
