package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.app.biz.order.ProductOrderPaySuccessHandler;
import com.github.wxiaoqi.security.app.biz.order.ReservationOrderPaySuccessHandler;
import com.github.wxiaoqi.security.app.mapper.BizAccountBookMapper;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付成功本地业务处理
 */
@Slf4j
@Component
public class PaySuccessBiz {


    @Autowired
    private ProductOrderPaySuccessHandler productOrderPaySuccessHandler;
    @Autowired
    private BizSubscribeWoBiz bizSubscribeWoBiz;
    @Resource
    private BizAccountBookMapper bizAccountBookMapper;
    @Autowired
    private ReservationOrderPaySuccessHandler reservationOrderPaySuccessHandler;

    public ObjectRestResponse doPayOrderFinish(PayOrderFinishIn payOrderFinishIn){

        if( log.isInfoEnabled()){
            log.info("doPayOrderFinish param:{}", JSON.toJSONString(payOrderFinishIn));
        }

         int busType = bizAccountBookMapper.getBusTypeByActualId(payOrderFinishIn.getActualId());
        ObjectRestResponse restResponse;
        if(AceDictionary.BUS_TYPE_PRODUCT_ORDER.equals(busType)){
            restResponse=   productOrderPaySuccessHandler.doPayOrderFinish(payOrderFinishIn);
        }else if(AceDictionary.BUS_TYPE_RESERVE_ORDER.equals(busType)){
            // 预约服务支付回调
            restResponse = reservationOrderPaySuccessHandler.doPayOrderFinish(payOrderFinishIn);
        }else{
            restResponse=  bizSubscribeWoBiz.doPayOrderFinish(payOrderFinishIn);
        }
        if( log.isInfoEnabled()){
            log.info("doPayOrderFinish result:{}", JSON.toJSONString(restResponse));
        }
        return restResponse;
    }
}
