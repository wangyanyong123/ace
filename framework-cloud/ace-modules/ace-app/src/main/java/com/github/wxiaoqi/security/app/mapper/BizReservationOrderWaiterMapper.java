package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReservationOrderWaiter;
import com.github.wxiaoqi.security.app.reservation.vo.ReservationOrderWaiterVO;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 服务订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 22:28:30
 */
public interface BizReservationOrderWaiterMapper extends CommonMapper<BizReservationOrderWaiter> {

    ReservationOrderWaiterVO queryWaiter(@Param("orderId") String orderId);
}
