package com.github.wxiaoqi.pay.wechat.service.impl;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.github.wxiaoqi.pay.biz.support.RefundParam;
import com.github.wxiaoqi.pay.wechat.biz.WechatNotifyBiz;
import com.github.wxiaoqi.pay.wechat.biz.WechatPayBiz;
import com.github.wxiaoqi.pay.wechat.biz.WechatRefundBiz;
import com.github.wxiaoqi.pay.wechat.service.WeChatPayService;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lzx
 * @Description: 微信支付
 * @Date: Created in 11:09 2018/12/7
 * @Modified By:
 */
@Service
@Slf4j
public class WeChatPayServiceImpl implements WeChatPayService {

    @Autowired
    private WechatNotifyBiz wechatNotifyBiz;

    @Autowired
    private WechatPayBiz wechatPayBiz;
    @Autowired
    private WechatRefundBiz wechatRefundBiz;

    @Override
    public String paynotify(String notityXml) {
        return wechatNotifyBiz.payNotify(notityXml);
    }

    @Override
    public ObjectRestResponse pay(GenerateUnifiedOrderIn generateUnifiedOrderIn) {


        return wechatPayBiz.pay(generateUnifiedOrderIn);
    }

    @Override
    public ObjectRestResponse refund(RefundParam refundParam) throws WeixinException {
        return wechatRefundBiz.refund(refundParam);
    }
}
