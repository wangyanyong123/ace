package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizReservationDelivery;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.ProductDeliveryData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 预约服务配送范围
 * 
 * @author guohao
 * @Date 2020-06-11 12:19:38
 */
public interface BizReservationDeliveryMapper extends CommonMapper<BizReservationDelivery> {

    int deleteByIds(@Param("deleteIdList") List<String> deleteIdList,
                    @Param("modifyBy") String modifyBy, @Param("deleteTime") Date deleteTime);

    int deleteByCityCode(@Param("productId") String productId,@Param("cityCodeList") List<String> cityCodeList,
                         @Param("modifyBy") String modifyBy, @Param("deleteTime") Date deleteTime);

    List<String> findDeliveryCityCodeList(@Param("productId") String productId);

    List<ProductDeliveryData> findDeliveryList(@Param("productId") String productId);
}
