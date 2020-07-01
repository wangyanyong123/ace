package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizCouponUse;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDiscount;
import com.github.wxiaoqi.security.app.mapper.BizCouponUseMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderDiscountMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@Service
public class BizProductOrderDiscountBiz extends BusinessBiz<BizProductOrderDiscountMapper,BizProductOrderDiscount> {

    @Resource
    private BizCouponUseMapper bizCouponUseMapper;

    public void backByOrderCancel(String orderId) {
        List<BizProductOrderDiscount> discountList = this.mapper.selectByOrderId(orderId, null);

        discountList.parallelStream()
                .filter(item-> AceDictionary.ORDER_DISCOUNT_TYPE_COUPON.equals(item.getDiscountType()))
                .forEach(item-> backCoupon(item.getRelationId(),AceDictionary.USER_COUPON_RECEIVED));
    }

    private void backCoupon(String couponId,String useStatus){
        BizCouponUse bizCouponUse = new BizCouponUse();
        bizCouponUse.setId(couponId);
        bizCouponUse.setUseStatus(useStatus);
        String handler = StringUtils.isNotEmpty(BaseContextHandler.getUserID())?  BaseContextHandler.getUserID():"admin";
        bizCouponUse.setModifyBy(handler);
        bizCouponUse.setModifyTime(new Date());
        bizCouponUseMapper.updateByPrimaryKeySelective(bizCouponUse);
    }
}