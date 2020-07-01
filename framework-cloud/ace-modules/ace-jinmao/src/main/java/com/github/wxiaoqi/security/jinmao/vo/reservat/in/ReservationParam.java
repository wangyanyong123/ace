package com.github.wxiaoqi.security.jinmao.vo.reservat.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultClassifyVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ReservationParam implements Serializable {

    @ApiModelProperty(value = "服务id")
    private String id;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "商品LOGO(不需要传)")
    private String productImage;
    @ApiModelProperty(value = "服务封面logo")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "商品精选(不需要传)")
    private String selectionImage;
    @ApiModelProperty(value = "商品精选图片")
    private List<ImgInfo> selectionImageList;
    @ApiModelProperty(value = "图文详情")
    private String reservationImagetextInfo;
    @ApiModelProperty(value = "项目范围")
    private List<ResultProjectVo> projectVo;
    @ApiModelProperty(value = "服务分类")
    private List<ResultClassifyVo> classifyVo;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;

    @ApiModelProperty(value = "单账号限制购买数量")
    private String limitNum;
    @ApiModelProperty(value = "商品库存数")
    private String productNum;
    @ApiModelProperty(value = "服务需知")
    private String reservationDesc;
    @ApiModelProperty(value = "服务热线")
    private String reservationTel;
    @ApiModelProperty(value = "服务日期范围")
    private String dataScopeVal;
    @ApiModelProperty(value = "上午开始时间")
    private String forenoonStartTime;
    @ApiModelProperty(value = "上午结束时间")
    private String forenoonEndTime;
    @ApiModelProperty(value = "上午库存数")
    private String productNumForenoon;
    @ApiModelProperty(value = "下午开始时间")
    private String afternoonStartTime;
    @ApiModelProperty(value = "下午结束时间")
    private String afternoonEndTime;
    @ApiModelProperty(value = "下午库存数")
    private String productNumAfternoon;
    @ApiModelProperty(value = "供应商")
    private String supplier;
    @ApiModelProperty(value = "销售方式")
    private String salesWay;

}
