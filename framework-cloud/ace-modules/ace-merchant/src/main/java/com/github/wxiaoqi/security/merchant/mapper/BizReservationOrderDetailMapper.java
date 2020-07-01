package com.github.wxiaoqi.security.merchant.mapper;

import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderDetail;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.merchant.vo.order.OrderStatusDO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预约服务订单详情表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 22:30:45
 */
public interface BizReservationOrderDetailMapper extends CommonMapper<BizReservationOrderDetail> {

    /**
     * 订单状态数据统计 除
     * @param tenantId
     * @return
     */
    List<OrderStatusDO> queryOderStatusTotal(@Param("tenantId") String tenantId);

    /**
     * 退款中统计
     * @param tenantId
     * @param detailRefundStatus
     * @return
     */
    OrderStatusDO queryRefundStatusTotal(@Param("tenantId") String tenantId,@Param("detailRefundStatus") Integer detailRefundStatus);

    /**
     * 待评价统计
     * @param tenantId
     * @param detailStatus
     * @param commentStatus
     * @return
     */
    OrderStatusDO queryCommentStatusTotal(@Param("tenantId") String tenantId,@Param("detailStatus") Integer detailStatus,@Param("commentStatus") Integer commentStatus,@Param("detailRefundStatus") Integer detailRefundStatus);
    /**
     * 昨日交易金额
     * @param tenantId
     * @return
     */
    BigDecimal yesterdayTotal(@Param("tenantId") String tenantId);

    /**
     * 今日订单数
     * @param tenantId
     * @return
     */
    int todayOrderTotal(@Param("tenantId") String tenantId);
}
