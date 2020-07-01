package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.AceXxlJobBiz;
import com.github.wxiaoqi.security.app.biz.BizOrderOperationRecordBiz;
import com.github.wxiaoqi.security.app.biz.order.ProductOrderPaySuccessHandler;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.entity.BizAccountBook;
import com.github.wxiaoqi.security.app.entity.BizCouponUse;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDiscount;
import com.github.wxiaoqi.security.app.entity.BizShoppingCart;
import com.github.wxiaoqi.security.app.mapper.BizAccountBookMapper;
import com.github.wxiaoqi.security.app.mapper.BizCouponUseMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderDiscountMapper;
import com.github.wxiaoqi.security.app.mapper.BizShoppingCartMapper;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: guohao
 * @create: 2020-04-19 21:37
 **/
@Component
public class ProductOrderAfterCreateHandler {
    @Resource
    private BizShoppingCartMapper bizShoppingCartMapper;
    @Resource
    private BizCouponUseMapper bizCouponUseMapper;
    @Resource
    private BizAccountBookMapper bizAccountBookMapper;
    @Resource
    private BizProductOrderDiscountMapper bizProductOrderDiscountMapper;

    @Autowired
    private ProductOrderPaySuccessHandler productOrderPaySuccessHandler;
    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;
    @Autowired
    private AceXxlJobBiz aceXxlJobBiz;

    public void execute(CreateOrderContext createOrderContext){
        CreateProductOrderContext context = (CreateProductOrderContext) createOrderContext;

        //3.维护账单表
        addAccountBook(context);
        //4.如果有加入购物车，则删除
        deleteShoppingCar(context);

        List<DiscountInfo> discountInfoList = context.getTenantList().stream()
                .filter(tenantInfo -> CollectionUtils.isNotEmpty(tenantInfo.getDiscountList()))
                .flatMap(tenantInfo -> tenantInfo.getDiscountList().stream())
                .collect(Collectors.toList());
        //5.设置优惠券状态为已使用
        updateCouponStatus(discountInfoList);

        //添加操作记录
        List<OrderIdResult> orderIdResultList = context.getOrderIdResultList();
        bizOrderOperationRecordBiz.addRecordByOrderIdResultList(orderIdResultList, OrderOperationType.CreateOrder,"");

        if(createOrderContext.isZeroOrder()){
            PayOrderFinishIn payOrderFinishIn =new PayOrderFinishIn();
            payOrderFinishIn.setPayId("");
            payOrderFinishIn.setActualId(createOrderContext.getBuyProductOutVo().getActualId());
            payOrderFinishIn.setTotalFee("0");
            payOrderFinishIn.setTotalFee(AceDictionary.PAY_TYPE_ZERO);
            payOrderFinishIn.setMchId("");
            payOrderFinishIn.setAppId("");
            productOrderPaySuccessHandler.doPayOrderFinish(payOrderFinishIn);
        }else{
            orderIdResultList.forEach(item->{
                aceXxlJobBiz.addProductOrderPayTimeoutJob(item.getOrderId());
            });
        }

    }

    private void addAccountBook(CreateProductOrderContext createOrderContext){
        BuyProductOutVo buyProductOutVo = createOrderContext.getBuyProductOutVo();
        BizAccountBook bizAccountBook = new BizAccountBook();
        bizAccountBook.setId(UUIDUtils.generateUuid());
        BigDecimal actualCost = createOrderContext.getTenantList().stream().map(TenantInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        if(BigDecimal.ZERO.compareTo(actualCost) == 0){
            bizAccountBook.setPayStatus(AceDictionary.PAY_STATUS_PAID);
            bizAccountBook.setPayType(AceDictionary.PAY_TYPE_ZERO);
            bizAccountBook.setPayDate(DateTimeUtil.getLocalTime());
        }else{
            bizAccountBook.setPayStatus(AceDictionary.PAY_STATUS_UN_PAID);
        }
        bizAccountBook.setSubId(buyProductOutVo.getSubId());
        bizAccountBook.setActualId(buyProductOutVo.getActualId());

        bizAccountBook.setActualCost(actualCost);
        bizAccountBook.setBusType(AceDictionary.BUS_TYPE_PRODUCT_ORDER);
        bizAccountBook.setTimeStamp(DateTimeUtil.getLocalTime());
        bizAccountBook.setCreateBy(BaseContextHandler.getUserID());
        bizAccountBook.setCreateTime(DateTimeUtil.getLocalTime());
        bizAccountBook.setModifyBy(bizAccountBook.getCreateBy());
        bizAccountBook.setModifyTime(bizAccountBook.getCreateTime());
        bizAccountBook.setStatus(AceDictionary.DATA_STATUS_VALID);
        bizAccountBookMapper.insertSelective(bizAccountBook);
    }

    private void deleteShoppingCar(CreateProductOrderContext createOrderContext){
        List<String> shoppingCarIdList = createOrderContext.getTenantList().stream()
                .flatMap(tenantInfo -> tenantInfo.getSkuList().stream())
                .map(SkuInfo::getShoppingCartId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(shoppingCarIdList)) {
            for (String shoppingCartId : shoppingCarIdList) {
                BizShoppingCart bizShoppingCart = new BizShoppingCart();
                bizShoppingCart.setId(shoppingCartId);
                bizShoppingCart.setStatus("0");
                bizShoppingCart.setTimeStamp(DateTimeUtil.getLocalTime());
                bizShoppingCart.setModifyTime(DateTimeUtil.getLocalTime());
                bizShoppingCart.setModifyBy(BaseContextHandler.getUserID());
                bizShoppingCartMapper.updateByPrimaryKeySelective(bizShoppingCart);
            }
        }
    }

    private void updateCouponStatus(List<DiscountInfo> discountInfos){
        List<DiscountInfo> discountInfoList = discountInfos.stream()
                .filter(discountInfo -> AceDictionary.ORDER_DISCOUNT_TYPE_COUPON == discountInfo.getDiscountType())
                .collect(Collectors.toList());
        for (DiscountInfo discountInfo : discountInfoList) {
            BizCouponUse bizCouponUse = new BizCouponUse();
            bizCouponUse.setId(discountInfo.getRelationId());
            bizCouponUse.setUseStatus(AceDictionary.USER_COUPON_USED);
            //这里不再指定优惠券与订单的关联关系。在订单优惠表中体现
            bizCouponUse.setOrderId("");
            String handler = StringUtils.isNotEmpty(BaseContextHandler.getUserID())?  BaseContextHandler.getUserID():"admin";
            bizCouponUse.setModifyBy(handler);
            bizCouponUse.setModifyTime(new Date());
            bizCouponUseMapper.updateByPrimaryKeySelective(bizCouponUse);
        }
    }
}
