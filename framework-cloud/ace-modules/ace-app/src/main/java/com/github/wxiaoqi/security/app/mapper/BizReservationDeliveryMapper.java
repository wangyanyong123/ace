package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReservationDelivery;
import com.github.wxiaoqi.security.app.vo.productdelivery.out.ProductDeliveryData;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预约服务配送范围
 * 
 * @author guohao
 * @Date 2020-06-11 12:21:50
 */
public interface BizReservationDeliveryMapper extends CommonMapper<BizReservationDelivery> {

    List<ProductDeliveryData> selectReservationDeliveryList(@Param("tenantId") String tenantId,
                                                        @Param("productIdList") List<String> productIdList);
}
