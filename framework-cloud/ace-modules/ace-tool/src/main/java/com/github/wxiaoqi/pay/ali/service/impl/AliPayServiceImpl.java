package com.github.wxiaoqi.pay.ali.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxiaoqi.feign.CommodityFeign;
import com.github.wxiaoqi.pay.ali.biz.AliAppPayBiz;
import com.github.wxiaoqi.pay.ali.biz.AliAppPayNotifyBiz;
import com.github.wxiaoqi.pay.ali.biz.AliRefundBiz;
import com.github.wxiaoqi.pay.ali.config.AliPayClientInitConfig;
import com.github.wxiaoqi.pay.ali.config.AlipayConfig;
import com.github.wxiaoqi.pay.ali.service.AliPayService;
import com.github.wxiaoqi.pay.biz.support.RefundParam;
import com.github.wxiaoqi.pay.mapper.AccountBookMapper;
import com.github.wxiaoqi.pay.mapper.SettlementMapper;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.api.vo.to.AliRefundTO;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:17 2018/12/7
 * @Modified By:
 */
@Slf4j
@Service
public class AliPayServiceImpl implements AliPayService {


    @Resource
    private SettlementMapper settlementMapper;
    @Autowired
    private AliAppPayBiz aliAppPayBiz;
    @Autowired
    private AliRefundBiz aliRefundBiz;
    @Autowired
    private AliAppPayNotifyBiz aliAppPayNotifyBiz;
    @Resource
    private AccountBookMapper accountBookMapper;

	@Override
	public boolean paynotify(Map<String, String> receiveMap) throws Exception {
		boolean signVerified = false;
		String partnerId = receiveMap.get("seller_id").toString();
		HashMap<String, String> aliMap = settlementMapper.selectALiByPropertyKey("alipay_partner", partnerId);
		if(aliMap!=null && !aliMap.isEmpty() && aliMap.get("aliPublicKey")!=null){
            String signType =  receiveMap.get("sign_type");
			String aliPublicKey = aliMap.get("aliPublicKey");
			signVerified = AlipaySignature.rsaCheckV1(receiveMap,
					aliPublicKey, AlipayConfig.CHARSET,
                    signType);
			if (signVerified) {
				String tradeStatus = receiveMap.get("trade_status");
				if ("TRADE_FINISHED".equals(tradeStatus)
						|| "TRADE_SUCCESS".equals(tradeStatus)) {
					String orderNoStr = receiveMap.get("out_trade_no").toString();
					return true;
				}
			}
		}
		return signVerified;
	}

	@Override
	public boolean paynotifyNew(Map<String, String> receiveMap) throws Exception {
		boolean signVerified = false;
		String appId = receiveMap.get("app_id").toString();
		HashMap<String, String> aliMap = settlementMapper.selectALiByPropertyKey("app_id", appId);
		if(aliMap!=null && !aliMap.isEmpty() && aliMap.get("appAliPublicKey")!=null){
			String signType =  receiveMap.get("sign_type");
			String appAliPublicKey = aliMap.get("appAliPublicKey");
			signVerified = AlipaySignature.rsaCheckV1(receiveMap,
					appAliPublicKey, AlipayConfig.CHARSET,
					signType);
			if (signVerified) {
				String tradeStatus = receiveMap.get("trade_status");
				if ("TRADE_FINISHED".equals(tradeStatus)
						|| "TRADE_SUCCESS".equals(tradeStatus)) {
					String orderNoStr = receiveMap.get("out_trade_no").toString();
					return true;
				}
			}
		}
		return signVerified;
	}

	/**
	 * alipay 统一下单
	 *
	 * @param generateUnifiedOrderIn 入参
	 * @return ObjectRestResponse
	 */
	@Override
	public ObjectRestResponse aliAppPay(GenerateUnifiedOrderIn generateUnifiedOrderIn) {
		BigDecimal actualCost= accountBookMapper.getActualCostByActualId(generateUnifiedOrderIn.getActualId());
		if (actualCost == null || actualCost.compareTo(generateUnifiedOrderIn.getActualPrice()) != 0) {
			ObjectRestResponse objectRestResponse = new ObjectRestResponse();
			objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
			objectRestResponse.setMessage("订单金额有误");
			objectRestResponse.setData(actualCost);

			if (log.isWarnEnabled()) {
				log.warn("订单金额有误，request:{},realActualCost:{}", JSON.toJSONString(generateUnifiedOrderIn), actualCost);
			}
			return objectRestResponse;
		}
		return aliAppPayBiz.aliAppPay(generateUnifiedOrderIn);
	}


	@Override
	public boolean paynotifyByPubCert(Map<String, String> params) throws AlipayApiException {
		return aliAppPayNotifyBiz.paynotifyByPubCert(params);
	}

	@Override
	public ObjectRestResponse refund(AliRefundTO aliRefundTO) {
		return aliRefundBiz.refundNew(aliRefundTO);
	}

    @Override
    public ObjectRestResponse refund(RefundParam refundParam) throws AlipayApiException {
        return aliRefundBiz.refund(refundParam);
    }
}
