package com.github.wxiaoqi.security.merchant.mapper;

import com.github.wxiaoqi.security.merchant.entity.BizReservationOrder;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.merchant.vo.order.reservation.ReservationOrderDetailVO;
import com.github.wxiaoqi.security.merchant.vo.order.reservation.ReservationOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预约服务订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 13:13:42
 */
public interface BizReservationOrderMapper extends CommonMapper<BizReservationOrder> {

    /**
     * 服务订单列表
     * @param tenantId
     * @param orderStatus
     * @param page
     * @param limit
     * @return
     */
    List<ReservationOrderVO> queryOrderListPage(@Param("tenantId") String tenantId,
                                                @Param("orderStatus") Integer orderStatus,
                                                @Param("refundStatus") Integer refundStatus,
                                                @Param("commentStatus") Integer commentStatus,
                                                @Param("keyword") String keyword,
                                                @Param("page") int page, @Param("limit") int limit);

    /**
     * 服务订单总记录数
     * @param tenantId
     * @param orderStatus
     * @return
     */
    int queryOrderListCount(@Param("tenantId") String tenantId,
                            @Param("orderStatus") Integer orderStatus,
                            @Param("refundStatus") Integer refundStatus,
                            @Param("commentStatus") Integer commentStatus,
                            @Param("keyword") String keyword);

    /**
     * 服务订单详情
     * @param orderId
     * @return
     */
    ReservationOrderDetailVO queryOrderDetail(@Param("orderId")String orderId,@Param("tenantId") String tenantId);
    
}
