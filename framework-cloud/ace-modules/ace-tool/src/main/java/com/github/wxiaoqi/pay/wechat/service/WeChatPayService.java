package com.github.wxiaoqi.pay.wechat.service;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.github.wxiaoqi.pay.biz.support.RefundParam;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.api.vo.to.AliRefundTO;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;

/**
 * @Author: Lzx
 * @Description: 微信支付
 * @Date: Created in 11:08 2018/12/7
 * @Modified By:
 */
public interface WeChatPayService {
    String paynotify(String notityXml) throws Exception;

    ObjectRestResponse pay(GenerateUnifiedOrderIn generateUnifiedOrderIn);

    ObjectRestResponse refund(RefundParam refundParam) throws WeixinException;
}
