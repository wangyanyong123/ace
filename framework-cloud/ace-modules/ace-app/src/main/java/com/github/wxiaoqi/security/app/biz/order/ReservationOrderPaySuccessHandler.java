package com.github.wxiaoqi.security.app.biz.order;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.app.biz.order.handler.ReservationOrderOperateHandler;
import com.github.wxiaoqi.security.app.mapper.BizReservationOrderMapper;
import com.github.wxiaoqi.security.app.vo.order.out.OrderDataForPaySuccess;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 处理商品订单支付成功业务逻辑
 *
 * @author: guohao
 * @create: 2020-04-20 11:32
 **/
@Slf4j
@Component
public class ReservationOrderPaySuccessHandler {


    @Resource
    private BizReservationOrderMapper bizReservationOrderMapper;
    @Resource
    private ReservationOrderOperateHandler reservationOrderOperateHandler;



    public ObjectRestResponse doPayOrderFinish(PayOrderFinishIn payOrderFinishIn) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();

        OrderDataForPaySuccess orderData = bizReservationOrderMapper.selectOneForPaySuccessByActualId(payOrderFinishIn.getActualId());
        if (orderData == null) {
            log.error("支付回调未找到有效的订单信息。param：{}", JSON.toJSONString(payOrderFinishIn));
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调 未找到有效的订单信息。");
            return objectRestResponse;
        }
        if(AceDictionary.PAY_STATUS_PAID.equals(orderData.getAccountBookPayStatus())){
            log.error("支付回调,交易账单支付状态有误。param：{}", JSON.toJSONString(payOrderFinishIn));
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调 订单尚未支付。");
            return objectRestResponse;
        }

        BigDecimal paidPrice = new BigDecimal(payOrderFinishIn.getTotalFee());
        if(paidPrice.compareTo(orderData.getActualPrice()) != 0){
            log.error("支付回调,支付金额有订单金额不一致。param：{}, orderPrice:{}"
                    , JSON.toJSONString(payOrderFinishIn),orderData.getActualPrice());
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调,支付金额有订单金额不一致. orderPrice:"+orderData.getActualPrice());
            return objectRestResponse;
        }
        if(!AceDictionary.ORDER_STATUS_W_PAY.equals(orderData.getOrderStatus())){
            log.error("支付回调 订单状态有误。param:{}, orderData：{}"
                    ,JSON.toJSONString(payOrderFinishIn), JSON.toJSONString(orderData) );
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调 订单状态有误。");
            return objectRestResponse;
        }

        // 更新订单状态，产品销量
        reservationOrderOperateHandler.paySuccess(orderData.getOrderId());

        return objectRestResponse;
    }


}
