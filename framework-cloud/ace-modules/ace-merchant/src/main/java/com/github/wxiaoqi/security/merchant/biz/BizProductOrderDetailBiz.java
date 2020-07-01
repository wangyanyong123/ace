package com.github.wxiaoqi.security.merchant.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.merchant.entity.BizProductOrderDetail;
import com.github.wxiaoqi.security.merchant.mapper.BizProductOrderDetailMapper;
import org.springframework.stereotype.Service;

/**
 * 订单产品表
 *
 * @author wangyanyong
 * @Date 2020-04-24 22:32:22
 */
@Service
public class BizProductOrderDetailBiz extends BusinessBiz<BizProductOrderDetailMapper,BizProductOrderDetail> {
    /**
     * 修改订单状态
     * @param updateProductOrderDetail
     * @return
     */
    public int updateStatus(BizProductOrderDetail updateProductOrderDetail){
        return this.mapper.updateStatus(updateProductOrderDetail);
    }
}