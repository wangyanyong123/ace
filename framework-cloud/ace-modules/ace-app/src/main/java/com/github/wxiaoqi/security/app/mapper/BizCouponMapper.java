package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCoupon;
import com.github.wxiaoqi.security.app.vo.coupon.CouponListVo;
import com.github.wxiaoqi.security.app.vo.coupon.CouponPriceVo;
import com.github.wxiaoqi.security.app.vo.coupon.UserCouponInfo;
import com.github.wxiaoqi.security.app.vo.postage.PostageVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券使用表
 * 
 * @Date 2019-04-16 10:49:40
 */
public interface BizCouponMapper extends CommonMapper<BizCoupon> {

    List<CouponListVo> getCouponList(@Param("productId") String productId);

    List<String> getViableCouponIds(@Param("companyId")String companyId,@Param("totalPrice")BigDecimal totalPrice,@Param("userId")String userId,@Param("couponId")String couponId);

    List<CouponPriceVo> getViableCouponList(@Param("couponIds") List<String> couponIds, @Param("productIds") List<String> productIds,@Param("useCouponId")String useCouponId);

    List<CouponListVo> getMyCoupon(@Param("userId") String userId,@Param("couponStatus") String couponStatus);

    CouponListVo getCouponInfo(@Param("id") String id);

    String getOneByCouponId(@Param("couponId") String couponId);

    int getUserCouponCount(@Param("couponId") String couponId, @Param("userId") String userId);

    int getSurplusCount(@Param("couponId") String couponId);

    List<PostageVo> getPostageInfo(String tenantId);

    int updateCouponStatusByUser(@Param("couponIds") List<String> couponIds);

    List<String> selectMyCouponIds(@Param("userId") String userId);

    UserCouponInfo getUserCouponInfo(@Param("userCouponId") String userCouponId);
}
