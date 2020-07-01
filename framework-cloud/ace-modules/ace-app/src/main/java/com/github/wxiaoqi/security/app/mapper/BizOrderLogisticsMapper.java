package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizOrderLogistics;
import com.github.wxiaoqi.security.app.vo.order.out.OrderLogisticsInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单物流信息表
 * 
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
public interface BizOrderLogisticsMapper extends CommonMapper<BizOrderLogistics> {

    List<OrderLogisticsInfo> selectLogisticsInfoByOrderId(@Param("orderId") String orderId);
}
