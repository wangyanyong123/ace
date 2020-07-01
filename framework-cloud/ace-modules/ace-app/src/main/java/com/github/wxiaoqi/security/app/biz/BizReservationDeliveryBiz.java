package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.vo.productdelivery.out.ProductDeliveryData;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizReservationDelivery;
import com.github.wxiaoqi.security.app.mapper.BizReservationDeliveryMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 预约服务配送范围
 *
 * @author guohao
 * @Date 2020-06-11 12:21:50
 */
@Service
public class BizReservationDeliveryBiz extends BusinessBiz<BizReservationDeliveryMapper,BizReservationDelivery> {
    public List<ProductDeliveryData> findReservationDeliveryList(String tenantId, List<String> productIdList) {
        return this.mapper.selectReservationDeliveryList(tenantId,productIdList);
    }
}
