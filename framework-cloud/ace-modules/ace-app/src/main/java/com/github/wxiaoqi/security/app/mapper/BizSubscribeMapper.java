package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.SubListForWebVo;
import com.github.wxiaoqi.security.api.vo.order.out.SubListVo;
import com.github.wxiaoqi.security.app.entity.BizSubscribe;
import com.github.wxiaoqi.security.app.vo.order.out.SubContactVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单表
 * 
 * @author zxl
 * @Date 2018-12-14 17:44:12
 */
public interface BizSubscribeMapper extends CommonMapper<BizSubscribe> {

    /**
     * 客户端APP查询我的订单列表
     * @param map
     * @return
     */
    List<SubListVo> getSubListForClientApp(Map<?, ?> map);

    /**
     * Web后台查询订单列表
     * @param map
     * @return
     */
    List<SubListForWebVo> querySubListByWeb(Map<?, ?> map);

    /**
     * Web后台查询订单列表记录总数
     * @param map
     * @return
     */
    int querySubListByWebTotal(Map<?, ?> map);

	String getByCompanyId(String companyId);

	String selectBusIdByProductId(String id);

	SubContactVo getSubContactById(String id);

    BigDecimal getActualCostById(String id);
}
