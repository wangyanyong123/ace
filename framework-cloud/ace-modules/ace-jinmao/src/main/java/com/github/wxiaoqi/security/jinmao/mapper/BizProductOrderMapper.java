package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizProductOrder;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultOrderList;
import com.github.wxiaoqi.security.jinmao.vo.productorder.in.QueryProductOrderIn;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderListVo;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderOperationVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品订单表
 * 
 * @author guohao
 * @Date 2020-04-27 18:57:18
 */
public interface BizProductOrderMapper extends CommonMapper<BizProductOrder> {

    List<ProductOrderListVo> selectProductOrderList(QueryProductOrderIn queryProductOrderIn);

    int countProductOrderList(QueryProductOrderIn queryProductOrderIn);

    List<ProductOrderDetailVo> selectProductOrderDetailVo(@Param("orderId") String orderId,@Param("tenantId") String tenantId);

    List<ProductOrderOperationVo> selectProductOrderOperationList(@Param("orderId") String orderId);

    List<ResultOrderList> selectOrderListForProductId(@Param("orderType") Integer orderType,@Param("productId") String productId);

    // 团购商品数量
    int getGroupProductCount(@Param("productId") String productId);

    // 查询导出的订单
    List<Map> exportProductOrderExcel(QueryProductOrderIn queryProductOrderIn);
    // 查询导出的商品详情
    List<Map> queryProductOrderDetailInfo(String orderId);

    // 获取优惠金额
    BigDecimal getDiscountPrice(@Param("orderId") String orderId,@Param("tenantId") String tenantId);

    // 获取运费
    BigDecimal getExpressPrice(@Param("orderId") String orderId,@Param("tenantId") String tenantId);
}
