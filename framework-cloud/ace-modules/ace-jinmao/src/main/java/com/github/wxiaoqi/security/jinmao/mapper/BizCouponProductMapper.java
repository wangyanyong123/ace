package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.api.vo.household.ProjectInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCouponProduct;
import com.github.wxiaoqi.security.jinmao.vo.coupon.in.ProjectInfo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.ProductInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 优惠券项目关联表
 *
 * @author huangxl
 * @Date 2019-04-16 17:42:34
 */
public interface BizCouponProductMapper extends CommonMapper<BizCouponProduct> {

    int deleteProduct(@Param("id") String id);


}
