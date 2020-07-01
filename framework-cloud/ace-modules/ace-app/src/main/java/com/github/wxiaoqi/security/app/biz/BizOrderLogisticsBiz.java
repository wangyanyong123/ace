package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.vo.order.out.OrderLogisticsInfo;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizOrderLogistics;
import com.github.wxiaoqi.security.app.mapper.BizOrderLogisticsMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 订单物流信息表
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@Service
public class BizOrderLogisticsBiz extends BusinessBiz<BizOrderLogisticsMapper,BizOrderLogistics> {

    public List<OrderLogisticsInfo> findLogisticsInfoByOrderId(String orderId) {
        return this.mapper.selectLogisticsInfoByOrderId(orderId);
    }
}