package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.SubProductInfo;
import com.github.wxiaoqi.security.api.vo.order.out.SubVo;
import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.app.reservation.dto.ReservationInfoDTO;
import com.github.wxiaoqi.security.app.entity.BizReservationOrder;
import com.github.wxiaoqi.security.app.vo.order.out.OrderDataForPaySuccess;
import com.github.wxiaoqi.security.app.vo.reservation.out.ReservationOrderListVO;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 预约服务订单表
 * 
 * @author huangxl
 * @Date 2020-04-19 17:01:24
 */
public interface BizReservationOrderMapper extends CommonMapper<BizReservationOrder> {

    /**
     * 查询预约服务规则信息
     * @param productId
     * @param userId
     * @return
     */
    ReservationInfoDTO selectReservationInfo(@Param("productId") String productId, @Param("userId") String userId);

    /**
     * 支付信息
     * @param actualId
     * @return
     */
    OrderDataForPaySuccess selectOneForPaySuccessByActualId(@Param("actualId") String actualId);

    /**
     * 预约服务列表
     * @param userId
     * @param orderStatus
     * @param refundStatus
     * @param commentStatus
     * @param page
     * @param limit
     * @return
     */
    List<ReservationOrderListVO> queryReservationOrderListPage(@Param("userId") String userId, @Param("orderStatus") Integer orderStatus,@Param("refundStatus") Integer refundStatus,@Param("commentStatus") Integer commentStatus, @Param("page") int page, @Param("limit") int limit);

    /**
     * 修改订单状态
     * @param orderStatus
     * @param orderId
     * @return
     */
    int updateStatus(@Param("orderId") String orderId, @Param("orderStatus") Integer orderStatus,
                     @Param("refundStatus") Integer refundStatus,
                     @Param("commentStatus") Integer commentStatus,
                     @Param("modifyBy") String modifyBy);

    // 以下三个方法退款审核详情的临时方法
    SubVo queryRefundProductOrder(@Param("orderId") String orderId);

    List<SubProductInfo> queryRefundProductOrderInfo(@Param("orderId") String orderId);

    List<TransactionLogVo> queryRefundProductOrderOperation(@Param("orderId") String orderId);
}
