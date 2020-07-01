package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.SubProductInfo;
import com.github.wxiaoqi.security.api.vo.order.out.SubToRobotProductInfo;
import com.github.wxiaoqi.security.app.entity.BizSubProduct;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单产品表
 * 
 * @author zxl
 * @Date 2018-12-14 17:44:12
 */
public interface BizSubProductMapper extends CommonMapper<BizSubProduct> {

    /**
     * 获取订单产品详情
     * @param subId
     * @return
     */
    List<SubProductInfo> getSubProductInfo(String subId);

    /**
     * 获取订单产品详情
     * @param subId
     * @return
     */
    List<SubToRobotProductInfo> getSubToRobotProductInfo(String subId);

    List<Map<String, Object>> getSubProductCountForDay(@Param("productId") String productId, @Param("day") String day);
}
