package com.github.wxiaoqi.security.merchant.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.merchant.entity.BizProductOrderDetail;
import com.github.wxiaoqi.security.merchant.vo.order.OrderStatusDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单产品表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 22:16:33
 */
public interface BizProductOrderDetailMapper extends CommonMapper<BizProductOrderDetail> {

    /**
     * 修改订单状态
     * @param updateProductOrderDetail
     * @return
     */
    int updateStatus(@Param("query") BizProductOrderDetail updateProductOrderDetail);
}
