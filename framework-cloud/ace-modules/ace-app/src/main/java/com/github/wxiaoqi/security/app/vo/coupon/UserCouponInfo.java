package com.github.wxiaoqi.security.app.vo.coupon;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: guohao
 * @create: 2020-04-19 13:39
 **/
@Data
public class UserCouponInfo {

    private String userCouponId;

    //使用状态(0-未领取,1-已领取,2-已使用,3-已退款,4-已过期)
    private String useStatus;

    private String userId;

    //优惠券类型(1-代金券2-折扣券)
    private String couponType;

    private Date startUseTime;

    private Date endUseTime;

    private BigDecimal faceValue;

    //商品适用范围(1-部分商品2-全部商品)
    private String productCover;

    //折扣力度
    private BigDecimal discountNum;

    //最大折扣金额
    private BigDecimal maxValue;

    //最小折扣金额
    private BigDecimal minValue;

    private String tenantId;

    private List<String> projectIdList;

    private List<String> productIdList;

    public BigDecimal getDiscountNum() {
        BigDecimal discount = discountNum;
        if(discount != null){
            discount = BigDecimal.TEN.subtract(discountNum).multiply(new BigDecimal("0.1"));
        }
        return discount;
    }
}
