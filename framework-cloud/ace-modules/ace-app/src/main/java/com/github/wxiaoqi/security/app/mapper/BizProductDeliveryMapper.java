package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductDelivery;
import com.github.wxiaoqi.security.app.vo.productdelivery.out.ProductDeliveryData;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品配送范围
 * 
 * @author guohao
 * @Date 2020-04-24 22:33:09
 */
public interface BizProductDeliveryMapper extends CommonMapper<BizProductDelivery> {

    List<ProductDeliveryData> findProductDeliveryList(@Param("tenantId") String tenantId,
                                                      @Param("productIdList") List<String> productIdList);
}
