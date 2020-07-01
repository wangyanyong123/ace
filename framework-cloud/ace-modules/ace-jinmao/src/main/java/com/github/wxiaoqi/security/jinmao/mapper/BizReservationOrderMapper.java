package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizReservationOrder;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.in.ReservationOrderQuery;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.out.ReservationOrderExcel;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.out.ReservationOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预约服务订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 13:13:42
 */
public interface BizReservationOrderMapper extends CommonMapper<BizReservationOrder> {

    /**
     * 服务订单列表
     * @param reservationOrderQuery
     * @return
     */
    List<ReservationOrderVO> queryReservationOrderPage(@Param("query") ReservationOrderQuery reservationOrderQuery);

    /**
     * 服务列表总数
     * @param reservationOrderQuery
     * @return
     */
    int queryReservationOrderCount(@Param("query") ReservationOrderQuery reservationOrderQuery);


    /**
     * 查询需要导出的订单
     * @param reservationOrderQuery
     * @return
     */
    List<Map<String, Object>> exportReservationOrder(@Param("query") ReservationOrderQuery reservationOrderQuery);

}
