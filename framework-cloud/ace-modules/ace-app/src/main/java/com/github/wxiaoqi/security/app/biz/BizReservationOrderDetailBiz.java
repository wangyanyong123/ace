package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizReservationOrderDetail;
import com.github.wxiaoqi.security.app.mapper.BizReservationOrderDetailMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 预约服务订单详情表
 *
 * @author huangxl
 * @Date 2020-04-19 17:01:24
 */
@Service
public class BizReservationOrderDetailBiz extends BusinessBiz<BizReservationOrderDetailMapper,BizReservationOrderDetail> {
}