package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductOrderDetail;
import com.github.wxiaoqi.security.app.vo.order.out.OrderDetailSalesQuantity;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderTenantInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 订单产品表
 * 
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
public interface BizProductOrderDetailMapper extends CommonMapper<BizProductOrderDetail> {


    List<OrderDetailSalesQuantity> selectSalesQuantityByParentId(@Param("parentId") String parentId);

    int updateOrderDetailStatusByOrderId(@Param("orderId") String orderId, @Param("odIdList") List<String> odIdList,
                                   @Param("targetStatus") Integer targetStatus, @Param("sourceStatus") Integer sourceStatus,
                                   @Param("modifyBy") String modifyBy);

    int updateOrderDetailBySplitOrder(@Param("sourceOrderId") String sourceOrderId,@Param("tenantId") String tenantId,
                                      @Param("targetOrderId") String targetOrderId, @Param("detailStatus") int detailStatus);

    List<String> selectUnCommentDetailIdList(@Param("orderId") String orderId);

    int updateCommentStatusByOrderId(@Param("orderId") String orderId, @Param("odIdList") List<String> odIdList,
                                     @Param("commentStatus") Integer commentStatus, @Param("modifyBy") String modifyBy);

    List<ProductOrderTenantInfo> selectProductOrderTenantInfoList(@Param("orderId") String orderId);

    int updateRefundStatusByOrderId(@Param("orderId") String orderId, @Param("odIdList") List<String> odIdList,
                                    @Param("refundStatus") Integer refundStatus, @Param("modifyBy") String modifyBy);

    List<BizProductOrderDetail> selectByOrderId(@Param("orderId") String orderId);

    List<BizProductOrderDetail> selectByParentId(@Param("parentId") String parentId);

    int updateDetailStatusAndRefundStatus(@Param("orderId") String orderId,@Param("odIdList") List<String> odIdList,
                              @Param("detailStatus") Integer detailStatus, @Param("refundStatus") Integer refundStatus,
                              @Param("modifyBy") String modifyBy);

}
