package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.api.vo.household.ProjectInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCoupon;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.CouponListVo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.ProductInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.ResultCoupon;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.UseSituationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券表
 * 
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
public interface BizCouponMapper extends CommonMapper<BizCoupon> {


    String getAccountByTenantId(@Param("tenantId") String tenantId);

    List<ProjectListVo> getProjectInfoByCoupon(@Param("id") String id);

    List<CouponListVo> getCouponList(@Param("tenantId")String tenantId,@Param("projectId") String projectId, @Param("searchVal") String searchVal,@Param("useStatus")String useStatus,
                                     @Param("page") Integer page, @Param("limit") Integer limit);

    int getCouponTotal(@Param("tenantId") String tenantId, @Param("searchVal") String searchVal, @Param("useStatus") String useStatus);

    ResultCoupon getCouponDetail(@Param("id") String id);

    List<String> getProjectByCouponId(@Param("id")String id);

    List<UseSituationVo> getUseSituation(@Param("couponId") String couponId,@Param("useStatus")String useStatus,
                                         @Param("page") Integer page, @Param("limit") Integer limit);

    int getUseSituationTotal(@Param("couponId") String couponId,@Param("useStatus")String useStatus);

    int getCouponUseCount(@Param("couponId") String couponId);

    List<ProductInfoVo> getProductByProject(@Param("type")String type,@Param("projectList") List<String> projectList,@Param("tenantId")String tenantId);

    int updateCouponUseStatus(@Param("couponId") String couponId);

    List<ProjectInfoVo> getCouponProject(@Param("couponId") String couponId);

    List<ProductInfoVo> getCouponProduct(@Param("couponId") String couponId);
}
