package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.vo.productdelivery.out.ProductDeliveryData;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizProductDelivery;
import com.github.wxiaoqi.security.app.mapper.BizProductDeliveryMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 商品配送范围
 *
 * @author guohao
 * @Date 2020-04-24 22:33:09
 */
@Service
public class BizProductDeliveryBiz extends BusinessBiz<BizProductDeliveryMapper,BizProductDelivery> {

    public List<ProductDeliveryData> findProductDeliveryList(String tenantId, List<String> productIdList) {
        return this.mapper.findProductDeliveryList(tenantId,productIdList);
    }

}