package com.github.wxiaoqi.security.jinmao.vo.coupon.out;

import com.github.wxiaoqi.security.api.vo.household.ProjectInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ResultCoupon implements Serializable {

    private static final long serialVersionUID = -6859433853710909764L;
    @ApiModelProperty(value = "id(保存不用传)")
    private String id;
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;
    @ApiModelProperty(value = "优惠券类型(1-代金券2-折扣券)")
    private String couponType;
    @ApiModelProperty(value = "优惠券开始使用时间(yyyy-MM-dd)")
    private String startUseTime;
    @ApiModelProperty(value = "优惠券结束使用时间(yyyy-MM-dd)")
    private String endUseTime;
    @ApiModelProperty(value = "优惠券总数")
    private Integer amount;
    @ApiModelProperty(value = "优惠券面值")
    private BigDecimal value;
    @ApiModelProperty(value = "折扣力度")
    private BigDecimal discountNum;
    @ApiModelProperty(value = "最高折扣金额")
    private BigDecimal masvalue;
    @ApiModelProperty(value = "最低消费金额")
    private BigDecimal minValue;
    @ApiModelProperty(value = "每人累计领取上限")
    private Integer getLimit;
    @ApiModelProperty(value = "关联项目")
    private List<ProjectInfoVo> projectInfo;
    @ApiModelProperty(value = "适用商品")
    private List<ProductInfoVo> productInfo;
    @ApiModelProperty(value = "封面图片")
    private String coverPhotoImage;
    private List<ImgInfo> coverPhoto;
    private String useStatus;
}
