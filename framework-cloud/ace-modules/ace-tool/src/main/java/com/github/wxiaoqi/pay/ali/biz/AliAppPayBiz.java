package com.github.wxiaoqi.pay.ali.biz;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.wxiaoqi.pay.ali.config.AliPayClientInitConfig;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AliAppPayBiz {

    @Autowired
    private AliPayClientInitConfig aliPayClientInitConfig;
    @Autowired
    private DefaultAlipayClient alipayClient;

    /**
     * @Author guohao
         * @param generateUnifiedOrderIn :
     * @return com.github.wxiaoqi.security.common.msg.ObjectRestResponse
     * @throws
     * @Date 2020/4/3 15:01
     */
    public ObjectRestResponse aliAppPay(GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        if (!AceDictionary.PAY_TYPE_ALIPAY.equals(generateUnifiedOrderIn.getPayType())) {
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("非法调用alipay.trade.app.pay ");
            return restResponse;
        }
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();

        AlipayTradeAppPayModel model = this.getAlipayTradeAppPayModel(generateUnifiedOrderIn);
        alipayRequest.setBizModel(model);
        alipayRequest.setNotifyUrl(aliPayClientInitConfig.getNotifyUrl());
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
            if (log.isDebugEnabled()) {
                log.debug("generateUnifiedOrder.response : {}", com.alibaba.fastjson.JSON.toJSONString(response));
            }
            restResponse.data(response.getBody());
        } catch (AlipayApiException e) {
            log.error("generateUnifiedOrder occur error: {}", e.getMessage(), e);
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("app统一下单失败");
        }
        return restResponse;
    }

    public AlipayTradeAppPayModel getAlipayTradeAppPayModel(GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。
        // 以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(null);
        model.setSubject(generateUnifiedOrderIn.getTitle());
        model.setOutTradeNo(generateUnifiedOrderIn.getActualId());
        model.setTotalAmount(generateUnifiedOrderIn.getActualPrice().toString());
        return model;

    }
}
