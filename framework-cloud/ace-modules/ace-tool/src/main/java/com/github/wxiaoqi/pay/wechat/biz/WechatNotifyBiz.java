package com.github.wxiaoqi.pay.wechat.biz;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;
import com.foxinmy.weixin4j.xml.XmlStream;
import com.github.wxiaoqi.pay.biz.NotifySuccessBiz;
import com.github.wxiaoqi.pay.biz.support.NotifySuccessParam;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: guohao
 * @create: 2020-04-09 16:55
 **/
@Slf4j
@Component
public class WechatNotifyBiz extends BaseWechatPayBiz {
    @Autowired
    private NotifySuccessBiz notifySuccessBiz;

    /**
     * @param notifyStr : 微信返回的支付回调信息
     * @return java.lang.String
     * @Author guohao
     * @Date 2020/4/9 17:24
     */
    public String payNotify(String notifyStr) {
        String result;
        try {
            Order order = ListsuffixResultDeserializer.deserialize(notifyStr, Order.class);
            if (log.isInfoEnabled()) {
                log.info("notifyOrderInfo: {}", JSON.toJSONString(order));
            }
            //验证签名
            String sign = order.getSign();
            String validSign = getWeixinPayProxyByAppId(order.getAppId()).getWeixinSignature().sign(order);

            if (!sign.equals(validSign)) {
                result = XmlStream.toXML(new XmlResult(Consts.FAIL, "签名错误"));
            } else {
                //处理本地业务
                doSuccessBusiness(order);
                result = XmlStream.toXML(new XmlResult(Consts.SUCCESS, "OK"));
            }
            if (log.isInfoEnabled()) {
                log.info("notifyOrderResult: {}", JSON.toJSONString(result));
            }
        } catch (Exception e) {
            log.error("微信支付回调异常。param:{}", notifyStr, e);
            result = XmlStream.toXML(new XmlResult(Consts.FAIL, "回调异常"));
        }
        return result;
    }

    private void doSuccessBusiness(Order order) {
        NotifySuccessParam param = new NotifySuccessParam();
        param.setAppId(order.getAppId());
        param.setMchId(order.getMchId());
        param.setPayType(AceDictionary.PAY_TYPE_WECHAT);
        param.setOutTradeNo(order.getOutTradeNo());
        param.setTradeNo(order.getTransactionId());
        param.setTotalAmount(order.getFormatTotalFee() + "");
        notifySuccessBiz.doNotifySuccessBusiness(param);
    }
}
