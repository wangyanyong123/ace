package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizOrderIncrement;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author guohao
 * @Date 2020-04-22 22:58:01
 */
public interface BizOrderIncrementMapper extends CommonMapper<BizOrderIncrement> {

    List<BizOrderIncrement> selectByOrderId(@Param("orderId") String orderId);


    int updateBySplitOrder(@Param("sourceOrderId") String sourceOrderId,@Param("tenantId") String tenantId,
                           @Param("targetOrderId") String targetOrderId);
}
