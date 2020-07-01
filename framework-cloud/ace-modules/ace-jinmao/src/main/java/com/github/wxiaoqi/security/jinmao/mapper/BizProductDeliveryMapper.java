package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizProductDelivery;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.ProductDeliveryData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 商品配送范围
 * 
 * @author guohao
 * @Date 2020-04-25 13:50:35
 */
public interface BizProductDeliveryMapper extends CommonMapper<BizProductDelivery> {

    List<String> selectIdList(String companyId, String productId);

    int deleteByIds(@Param("deleteIdList") List<String> deleteIdList,
                    @Param("modifyBy") String modifyBy, @Param("deleteTime") Date deleteTime);

    int deleteByCityCode(@Param("productId") String productId,@Param("cityCodeList") List<String> cityCodeList,
                    @Param("modifyBy") String modifyBy, @Param("deleteTime") Date deleteTime);

    List<String> findProductDeliveryCityCodeList(@Param("productId") String productId);

    List<ProductDeliveryData> findProductDeliveryList(@Param("productId") String productId);
}
