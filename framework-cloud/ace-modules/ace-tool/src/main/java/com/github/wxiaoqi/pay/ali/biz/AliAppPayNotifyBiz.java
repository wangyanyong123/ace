package com.github.wxiaoqi.pay.ali.biz;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.gexin.fastjson.JSON;
import com.github.wxiaoqi.pay.ali.config.AliPayClientInitConfig;
import com.github.wxiaoqi.pay.biz.NotifySuccessBiz;
import com.github.wxiaoqi.pay.biz.support.NotifySuccessParam;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AliAppPayNotifyBiz {
    @Autowired
    private AliPayClientInitConfig aliPayClientInitConfig;
    @Autowired
    private NotifySuccessBiz notifySuccessBiz;

    public boolean paynotifyByPubCert(Map<String, String> params) throws AlipayApiException {
        if (log.isInfoEnabled()) {
            log.info("支付宝支付回调 request:{}}", JSON.toJSONString(params));
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCertCheckV1(Map<String, String> params, String publicKeyCertPath, String charset,String signType)
        boolean flag = AlipaySignature.rsaCertCheckV1(params, aliPayClientInitConfig.getAlipayCertPath(),
                aliPayClientInitConfig.getCharset(), aliPayClientInitConfig.getSignType());
        if (flag) {
            String outTradeNo = params.get("out_trade_no");
            String tradeNo = params.get("trade_no");
            String totalAmount = params.get("total_amount");
            String appId = params.get("app_id");
            String mchId = params.get("seller_id");
            try {
                doSuccessBusiness(outTradeNo, tradeNo, totalAmount, mchId, appId);
                return true;
            } catch (Exception e) {
                log.error("支付宝支付回调本地业务执行失败。 param:{}", JSON.toJSONString(params), e);
            }
        }
        if (log.isWarnEnabled()) {
            log.warn("支付宝回调验签失败,param:{}", JSON.toJSONString(params));
        }
        return false;
    }

    private void doSuccessBusiness(String outTradeNo, String tradeNo, String totalFee, String mchId, String appId) {
        NotifySuccessParam param = new NotifySuccessParam();
        param.setAppId(appId);
        param.setMchId(mchId);
        param.setPayType(AceDictionary.PAY_TYPE_ALIPAY);
        param.setOutTradeNo(outTradeNo);
        param.setTradeNo(tradeNo);
        param.setTotalAmount(totalFee);
        notifySuccessBiz.doNotifySuccessBusiness(param);
    }
}
