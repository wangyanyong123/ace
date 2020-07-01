package com.github.wxiaoqi.pay.ali.service;

import com.alipay.api.AlipayApiException;
import com.github.wxiaoqi.pay.biz.support.RefundParam;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.api.vo.to.AliRefundTO;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;

import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:15 2018/12/7
 * @Modified By:
 */
public interface AliPayService {
	boolean paynotify(Map<String,String> receiveMap) throws Exception;

	boolean paynotifyNew(Map<String,String> receiveMap) throws Exception;

	ObjectRestResponse aliAppPay(GenerateUnifiedOrderIn generateUnifiedOrderIn);

	/**
	 * 支付宝基于公钥证书回调验签方式
	 * @param receiveMap 支付宝回调数据
	 * @return boolean
	 */
	boolean paynotifyByPubCert(Map<String, String> receiveMap) throws AlipayApiException;

	ObjectRestResponse refund(AliRefundTO aliRefundTO);

	/**
	 * 新版本退款接口，支持部分退款
	 * @Author guohao
	 * @param refundParam : 入参
	 * @return com.github.wxiaoqi.security.common.msg.ObjectRestResponse
	 * @Date 2020/4/11 16:24
	 */
    ObjectRestResponse refund(RefundParam refundParam) throws AlipayApiException;
}
