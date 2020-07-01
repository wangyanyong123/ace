package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.entity.BizAccountBook;
import com.github.wxiaoqi.security.app.entity.BizCouponUse;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderDiscount;
import com.github.wxiaoqi.security.app.entity.BizShoppingCart;
import com.github.wxiaoqi.security.app.mapper.BizAccountBookMapper;
import com.github.wxiaoqi.security.app.mapper.BizCouponUseMapper;
import com.github.wxiaoqi.security.app.mapper.BizReservationOrderDiscountMapper;
import com.github.wxiaoqi.security.app.mapper.BizShoppingCartMapper;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: guohao
 * @create: 2020-04-19 21:37
 **/
@Component
public class ReservationOrderAfterCreateHandler {
    @Resource
    private BizCouponUseMapper bizCouponUseMapper;
    @Resource
    private BizAccountBookMapper bizAccountBookMapper;
    @Resource
    private BizReservationOrderDiscountMapper bizReservationOrderDiscountMapper;

    public void execute(CreateReservationOrderContext createOrderContext){
        //3.维护账单表
        addAccountBook(createOrderContext);
        DiscountInfo discountInfo = createOrderContext.getReservationTenantInfo().getDiscountInfo();

        if(!ObjectUtils.isEmpty(discountInfo)){
            //5.设置优惠券状态为已使用
            updateCouponStatus(discountInfo);
            // 增加订单优惠记录
            addOrderDiscountRecord(discountInfo);
        }

    }

    private void addAccountBook(CreateReservationOrderContext createOrderContext){
        BuyProductOutVo buyProductOutVo = createOrderContext.getBuyProductOutVo();
        BizAccountBook bizAccountBook = new BizAccountBook();
        bizAccountBook.setId(UUIDUtils.generateUuid());
        bizAccountBook.setPayStatus("1");
        bizAccountBook.setSubId(buyProductOutVo.getSubId());
        bizAccountBook.setActualId(buyProductOutVo.getActualId());
        bizAccountBook.setBusType(2);

        ReservationTenantInfo reservationTenantInfo = createOrderContext.getReservationTenantInfo();

        BigDecimal actualCost = ObjectUtils.isEmpty(reservationTenantInfo.getTotalPrice())?BigDecimal.ZERO: reservationTenantInfo.getTotalPrice();
        bizAccountBook.setActualCost(actualCost);
        bizAccountBook.setTimeStamp(DateTimeUtil.getLocalTime());
        bizAccountBook.setCreateBy(BaseContextHandler.getUserID());
        bizAccountBook.setCreateTime(DateTimeUtil.getLocalTime());
        bizAccountBook.setModifyBy(bizAccountBook.getCreateBy());
        bizAccountBook.setModifyTime(bizAccountBook.getCreateTime());
        bizAccountBook.setStatus(AceDictionary.DATA_STATUS_VALID);
        bizAccountBookMapper.insertSelective(bizAccountBook);
    }



    private void updateCouponStatus(DiscountInfo discountInfo){
         if(AceDictionary.ORDER_DISCOUNT_TYPE_COUPON == discountInfo.getDiscountType()){
             BizCouponUse bizCouponUse = new BizCouponUse();
             bizCouponUse.setId(discountInfo.getRelationId());
             bizCouponUse.setUseStatus("2");
             bizCouponUseMapper.updateByPrimaryKeySelective(bizCouponUse);
         }
    }

    private void addOrderDiscountRecord(DiscountInfo discountInfo){
            BizReservationOrderDiscount discount = new BizReservationOrderDiscount();
            discount.setId(UUIDUtils.generateUuid());
            discount.setDiscountType(discountInfo.getDiscountType());
            discount.setDiscountPrice(discountInfo.getDiscountPrice());
            discount.setOrderRelationType(AceDictionary.DISCOUNT_RELATION_TYPE_ORDER);
            discount.setCreateBy(BaseContextHandler.getUserID());
            discount.setCreateTime(DateTimeUtil.getLocalTime());
            discount.setModifyBy(discount.getCreateBy());
            discount.setStatus(AceDictionary.DATA_STATUS_VALID);
            discount.setRelationId(discountInfo.getRelationId());
            discount.setOrderId(discountInfo.getOrderId());
            bizReservationOrderDiscountMapper.insertSelective(discount);
    }
}
