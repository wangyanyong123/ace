package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.SubProductInfo;
import com.github.wxiaoqi.security.api.vo.order.out.SubVo;
import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.app.vo.order.in.QueryOrderListIn;
import com.github.wxiaoqi.security.app.vo.order.out.OrderDataForPaySuccess;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderListVo;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 商品订单表
 * 
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
public interface BizProductOrderMapper extends CommonMapper<BizProductOrder> {

    List<ProductOrderListVo> selectOrderList(QueryOrderListIn queryOrderListIn);

    List<OrderDataForPaySuccess> selectListForPaySuccessByActualId(@Param("actualId") String actualId);

    OrderDataForPaySuccess selectOneForPaySuccessByActualId(@Param("actualId") String actualId);

    /**
     * 团购完成 更新团购订单状态
     * @param orderId 订单id
     * @return int
     */
    int updateOrderStatusByGroupComplete(@Param("orderId") String orderId,@Param("modifyBy") String modify);

    int updateInValid(@Param("orderId") String orderId, @Param("modifyBy") String modifyBy, @Param("modifyTime") Date modifyTime);

    List<OrderIdResult> selectOrderIdListGroupWaitingComplete(@Param("productId") String productId);

    int updateOrderStatusById(@Param("orderId") String orderId, @Param("targetStatus") Integer targetStatus,
                                         @Param("sourceStatus") Integer sourceStatus, @Param("modifyBy") String modifyBy);

    int getPurchasedCount(@Param("productId") String productId, @Param("userId") String userId);

    // 以下三个方法退款审核详情的临时方法
    SubVo queryRefundProductOrder(@Param("orderId") String orderId);

    List<SubProductInfo> queryRefundProductOrderInfo(@Param("orderId") String orderId);

    List<TransactionLogVo> queryRefundProductOrderOperation(@Param("orderId") String orderId);

}
