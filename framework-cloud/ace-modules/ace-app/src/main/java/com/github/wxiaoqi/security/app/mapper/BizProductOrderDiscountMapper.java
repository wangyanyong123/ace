package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductOrderDiscount;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
public interface BizProductOrderDiscountMapper extends CommonMapper<BizProductOrderDiscount> {

    List<BizProductOrderDiscount> selectByOrderId(@Param("orderId") String orderId
            ,@Param("orderRelationType") Integer orderRelationType);

    int updateBySplitOrder(@Param("sourceOrderId") String sourceOrderId,@Param("tenantId") String tenantId,
                           @Param("targetOrderId") String targetOrderId);
}
