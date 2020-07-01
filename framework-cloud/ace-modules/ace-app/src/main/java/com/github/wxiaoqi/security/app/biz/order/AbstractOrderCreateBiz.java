package com.github.wxiaoqi.security.app.biz.order;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.order.context.CreateOrderContext;
import com.github.wxiaoqi.security.app.biz.order.context.CreateProductOrderContext;
import com.github.wxiaoqi.security.app.biz.order.context.CreateReservationOrderContext;
import com.github.wxiaoqi.security.app.biz.order.handler.ProductOrderStoreHandler;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: guohao
 * @create: 2020-04-18 12:03
 **/
@Slf4j
public abstract class AbstractOrderCreateBiz implements IOrderBiz{

    @Autowired
    private ProductOrderStoreHandler productOrderStoreHandler;

    @Autowired
    private ReservationOrderCreateBiz reservationOrderCreateBiz;
    @Override
    public BuyProductOutVo createOrder(BuyProductInfo buyProductInfo) {
        //校验参数
        checkParam(buyProductInfo);
        //创建上下文
        CreateOrderContext createOrderContext = buildContext(buyProductInfo);
        //业务验证
        beforeCreate(createOrderContext);
        String orderId = UUIDUtils.generateUuid();
        createOrderContext.setOrderId(orderId);
        //扣减库存
        ObjectRestResponse objectRestResponse = reduceStock(createOrderContext);
        if(log.isInfoEnabled()){
            log.info("下单扣减库存响应结果：{}-{}",objectRestResponse.getStatus(),objectRestResponse.getMessage());
        }
        if(!objectRestResponse.success()){
            throw new BusinessException(objectRestResponse.getMessage());
        }
        try {
            //创建订单
            doCreate(createOrderContext);
            //执行创建订单后续逻辑
            afterCreate(createOrderContext);

        }catch (Exception e){
            log.error("创建订单异常.",e);
            backStock(createOrderContext);
            throw new BusinessException(e.getMessage());
        }
        return createOrderContext.getBuyProductOutVo();
    }

    protected abstract void checkParam(BuyProductInfo buyProductInfo);

    protected  abstract void beforeCreate(CreateOrderContext createOrderContext);

    protected abstract CreateOrderContext buildContext(BuyProductInfo buyProductInfo);

    protected abstract BuyProductOutVo doCreate(CreateOrderContext createOrderContext);

    protected  abstract void afterCreate(CreateOrderContext createOrderContext);

    private ObjectRestResponse reduceStock(CreateOrderContext createOrderContext){
        if(createOrderContext instanceof CreateProductOrderContext){
          return  productOrderStoreHandler.reduceRedisStock(createOrderContext);
        }else if(createOrderContext instanceof CreateReservationOrderContext){
            return reservationOrderCreateBiz.reduceStock(createOrderContext);
        }else{
            log.error("未知的下单请求，context:{}", JSON.toJSONString(createOrderContext));
            ObjectRestResponse objectRestResponse = new ObjectRestResponse();
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("未知的下单请求");
            return objectRestResponse;
        }
    }
    private void  backStock(CreateOrderContext createOrderContext){
        try {
            if(createOrderContext instanceof CreateProductOrderContext){
                productOrderStoreHandler.backRedisStock(createOrderContext);
            }else if(createOrderContext instanceof CreateReservationOrderContext){
                reservationOrderCreateBiz.backStock(createOrderContext);
            }
        }catch (Exception e){
            log.error("下单失败归还库存失败, context:{}",JSON.toJSONString(createOrderContext),e);
        }

    }

}
