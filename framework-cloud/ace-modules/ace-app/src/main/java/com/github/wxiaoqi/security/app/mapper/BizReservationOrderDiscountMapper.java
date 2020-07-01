package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReservationOrderDiscount;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

/**
 * 
 * 
 * @author huangxl
 * @Date 2020-04-20 16:45:22
 */
public interface BizReservationOrderDiscountMapper extends CommonMapper<BizReservationOrderDiscount> {

    /**
     * 获取优惠券ID
     * @param orderId
     * @return
     */
    String querRalationIdByOrderId(String orderId);
}
