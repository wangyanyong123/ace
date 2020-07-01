package com.github.wxiaoqi.security.jinmao.vo.coupon.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CouponParams implements Serializable {
    private static final long serialVersionUID = -7362884108719959268L;

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
    private String amount;
    @ApiModelProperty(value = "优惠券面值")
    private String value;
    @ApiModelProperty(value = "折扣力度")
    private String discountNum;
    @ApiModelProperty(value = "最高折扣金额")
    private String masvalue;
    @ApiModelProperty(value = "最低消费金额")
    private String minValue;
    @ApiModelProperty(value = "每人累计领取上限")
    private String getLimit;
    @ApiModelProperty(value = "关联项目")
    private List<String> projectId;
    @ApiModelProperty(value = "适用商品")
    private List<String> productId;
    @ApiModelProperty(value = "封面图片")
    private List<ImgInfo> coverPhoto;




}
