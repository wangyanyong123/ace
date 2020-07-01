package com.github.wxiaoqi.security.merchant.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.merchant.entity.BizProductOrder;
import com.github.wxiaoqi.security.merchant.vo.order.product.ProductOrderDetail;
import com.github.wxiaoqi.security.merchant.vo.order.product.ProductOrderDetailVO;
import com.github.wxiaoqi.security.merchant.vo.order.product.ProductOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

/**
 * 商品订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 13:13:28
 */
public interface BizProductOrderMapper extends CommonMapper<BizProductOrder> {


    /**
     * 查询商品订单
     * @param tenantId
     * @param detailStatus
     * @param page
     * @param limit
     * @return
     */
    List<ProductOrderVO> queryOrderListPage(@Param("tenantId") String tenantId,
                                            @Param("detailStatus") Integer detailStatus,
                                            @Param("detailRefundStatus") Integer detailRefundStatus,
                                            @Param("commentStatus") Integer commentStatus,
                                            @Param("keyword") String keyword,
                                            @Param("page") int page, @Param("limit") int limit);

    /**
     * 查询商品订单总记录数
     * @param tenantId
     * @return
     */
    int queryOrderListCount(@Param("tenantId") String tenantId,
                            @Param("detailStatus") Integer detailStatus,
                            @Param("detailRefundStatus") Integer detailRefundStatus,
                            @Param("commentStatus") Integer commentStatus,
                            @Param("keyword") String keyword);

    /**
     * 商品订单详情
     * @param orderId
     * @param tenantId
     * @return
     */
    ProductOrderDetailVO queryOrderDetail(@Param("orderId") String orderId,@Param("tenantId") String tenantId);

    /**
     * 查询订单中的商品
     * @param orderId
     * @param tenantId
     * @return
     */
    List<ProductOrderDetail> queryOrderDetailList(@Param("orderId") String orderId, @Param("tenantId") String tenantId);

}
