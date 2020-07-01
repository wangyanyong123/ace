package com.github.wxiaoqi.security.app.biz.order;

import com.github.wxiaoqi.security.app.biz.order.handler.ReservationOrderOperateHandler;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 订单退款成功处理器
 */
@Slf4j
@Component
public class OrderRefundHandler {

    @Autowired
    private ProductOrderOperateBiz productOrderOperateBiz;
    @Autowired
    private ReservationOrderOperateHandler reservationOrderOperateHandler;

    public void refundReject(String subId,Integer busType,String handler){
        Assert.hasLength(subId,"subId不能为空");
        Assert.notNull(busType,"busType不能为null");
        if(!AceDictionary.BUS_TYPE.containsKey(busType)){
            throw  new BusinessException("非法的业务类型，"+busType);
        }

        ObjectRestResponse objectRestResponse ;

        if(AceDictionary.BUS_TYPE_PRODUCT_ORDER.equals(busType)){
            objectRestResponse=  productOrderOperateBiz.refundReject(subId,handler);
        }else if(AceDictionary.BUS_TYPE_RESERVE_ORDER.equals(busType)){
            objectRestResponse = reservationOrderOperateHandler.returnFail(subId);
        } else{
            objectRestResponse = new ObjectRestResponse();
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("不支持的业务类型，subId:"+subId+",busType:"+busType);
        }
        if(!objectRestResponse.success()){
            log.error("执行订单退款成功业务失败。message:{},subId:{}",objectRestResponse.getMessage(),subId);
        }
    }

    public void refundSuccess(String subId,Integer busType,String handler){
        Assert.hasLength(subId,"subId不能为空");
        Assert.notNull(busType,"busType不能为null");
        if(!AceDictionary.BUS_TYPE.containsKey(busType)){
            throw  new BusinessException("非法的业务类型，"+busType);
        }

        ObjectRestResponse objectRestResponse ;

        if(AceDictionary.BUS_TYPE_PRODUCT_ORDER.equals(busType)){
            objectRestResponse = doProductOrderRefundSuccess(subId,handler);
        }else if(AceDictionary.BUS_TYPE_RESERVE_ORDER.equals(busType)){
            objectRestResponse = doReserveOrderRefundSuccess(subId,handler);
        } else{
            objectRestResponse = new ObjectRestResponse();
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("不支持的业务类型，subId:"+subId+",busType:"+busType);
        }
        if(!objectRestResponse.success()){
            log.error("执行订单退款成功业务失败。message:{},subId:{}",objectRestResponse.getMessage(),subId);
        }

    }

    private ObjectRestResponse doProductOrderRefundSuccess(String subId,String handler){
        return productOrderOperateBiz.refundSuccess(subId,handler);
    }

    private ObjectRestResponse doReserveOrderRefundSuccess(String subId,String handler){
        return reservationOrderOperateHandler.returnSuccess(subId);
    }

}
