package com.github.wxiaoqi.pay.ali.biz;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.wxiaoqi.pay.ali.config.AliPayClientInitConfig;
import com.github.wxiaoqi.pay.ali.config.AlipayConfig;
import com.github.wxiaoqi.pay.biz.support.RefundParam;
import com.github.wxiaoqi.pay.mapper.AccountBookMapper;
import com.github.wxiaoqi.security.api.vo.to.AliRefundTO;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@Component
public class AliRefundBiz {
    @Autowired
    private AliPayClientInitConfig aliPayClientInitConfig;
    @Autowired
    private DefaultAlipayClient alipayClient;
    @Resource
    private AccountBookMapper accountBookMapper;

    /**
     * 公钥证书加密版本
     *
     * @param aliRefundTO 入参
     * @return ObjectRestResponse
     */
    public ObjectRestResponse refundNew(AliRefundTO aliRefundTO) {
        ObjectRestResponse response = new ObjectRestResponse();
        //因为新支付宝账号配置在apollo中，如果从表中可以找到配置信息，为老版本支付
        HashMap<String, String> aliMap = accountBookMapper.selectALiByByActualId(aliRefundTO.getOutTradeNo());
        if (aliMap != null && !aliMap.isEmpty()) {
            return refundOld(aliRefundTO);
        }

        HashMap<String, String> map = accountBookMapper.selectAccountBookByActualId(aliRefundTO.getOutTradeNo());
        while (true) {
            String outRequestNo = UUIDUtils.generateUuid();
            if (accountBookMapper.getNumByOutRequestNo(outRequestNo) < 1) {
                aliRefundTO.setOutRequestNo(outRequestNo);
                break;
            }
        }
        if (map != null) {
            String payId = map.getOrDefault("payId", "");
            String actualId = map.getOrDefault("actualId", "");
            String payStatus = map.getOrDefault("payStatus", "");
            String refundStatus = map.getOrDefault("refundStatus", "");
            Object ob = map.getOrDefault("actualCost", "");
            String actualCost = ob.toString();
            if ("2".equals(payStatus)) {
                if ("1".equals(refundStatus)) {
                    log.info("该笔订单正在退款中");
                    response.setStatus(510);
                    response.setMessage("该笔订单正在退款中");
                    return response;
                } else if ("2".equals(refundStatus)) {
                    log.info("该笔订单已经退款成功了");
                    response.setStatus(511);
                    response.setMessage("该笔订单已经退款成功了");
                    return response;
                } else {
                    if (StringUtils.isEmpty(actualCost) || new BigDecimal(actualCost).compareTo(BigDecimal.ZERO) == 0) {
                        log.info("当前退款金额为0，无需退款！");
                        if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                            accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "2", actualCost, aliRefundTO.getRefundReason(), null, aliRefundTO.getOutRequestNo());
                        } else {
                            log.info("虚假请求");
                            response.setStatus(502);
                            response.setMessage("该订单是虚假订单！");
                            return response;
                        }
                        response.setMessage("当前退款金额为0，无需退款！");
                        return response;
                    } else {
                        if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                            accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "1", null, aliRefundTO.getRefundReason(), null, aliRefundTO.getOutRequestNo());
                        } else {
                            log.info("虚假请求");
                            response.setStatus(503);
                            response.setMessage("该订单是虚假订单！");
                            return response;
                        }
                        AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();
                        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
                        model.setOutTradeNo(aliRefundTO.getOutTradeNo());
                        model.setTradeNo(payId);
                        model.setRefundAmount(actualCost);
                        model.setRefundReason(aliRefundTO.getRefundReason());
                        model.setOutRequestNo(aliRefundTO.getOutRequestNo());
                        model.setOperatorId("OP001");
                        model.setStoreId("NJ_S_001");
                        model.setTerminalId("NJ_T_001");
                        alipay_request.setBizModel(model);
                        try {
                            AlipayTradeRefundResponse alipay_response = alipayClient.certificateExecute(alipay_request);
                            log.info("调用支付宝退款返回结果>>>>>>>>>>>>>>>>" + JSON.toJSONString(alipay_response));
                            if (alipay_response.isSuccess()) {
                                log.info("调用支付宝退款成功");
                                if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                    accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "2", actualCost, null, null, null);
                                } else {
                                    log.info("虚假请求");
                                    response.setStatus(504);
                                    response.setMessage("该订单是虚假订单！");
                                    return response;
                                }
                                response.setMessage("退款成功");
                                return response;
                            } else {
                                log.info("调用支付宝退款失败>>>>" + JSON.toJSONString(alipay_response));
                                if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                    accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "3", null, null, alipay_response.getSubMsg(), null);
                                } else {
                                    log.info("虚假请求");
                                    response.setStatus(505);
                                    response.setMessage("该订单是虚假订单！");
                                    return response;
                                }
                                response.setStatus(512);
                                response.setMessage("退款失败");
                                return response;
                            }
                        } catch (Exception e) {
                            log.error("支付宝退款错误！", e);
                            if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "3", null, null, "调用支付宝异常！", null);
                            } else {
                                log.info("虚假请求");
                                response.setStatus(505);
                                response.setMessage("该订单是虚假订单！");
                                return response;
                            }
                            e.printStackTrace();
                            response.setStatus(521);
                            response.setMessage("调用支付宝异常！");
                            return response;
                        }
                    }
                }
            } else {
                log.info("该笔订单还未支付过");
                response.setStatus(509);
                response.setMessage("该笔订单还未支付过");
                return response;
            }
        } else {
            response.setStatus(508);
            response.setMessage("未查询到该笔订单的支付信息！");
            return response;
        }
    }

    /**
     * 普通公钥加密版本
     *
     * @param aliRefundTO 入参
     * @return ObjectRestResponse
     */
    public ObjectRestResponse refundOld(AliRefundTO aliRefundTO) {
        ObjectRestResponse response = new ObjectRestResponse();
        HashMap<String, String> aliMap = accountBookMapper.selectALiByByActualId(aliRefundTO.getOutTradeNo());
        if (aliMap != null && !aliMap.isEmpty() && aliMap.get("appAliPublicKey") != null
                && aliMap.get("appId") != null && aliMap.get("appRsa2") != null) {
            if (aliRefundTO.getOutTradeNo() != null) {
                HashMap<String, String> map = accountBookMapper.selectAccountBookByActualId(aliRefundTO.getOutTradeNo());
                while (true) {
                    String outRequestNo = UUIDUtils.generateUuid();
                    if (accountBookMapper.getNumByOutRequestNo(outRequestNo) < 1) {
                        aliRefundTO.setOutRequestNo(outRequestNo);
                        break;
                    }
                }
                if (map != null) {
                    String payId = map.get("payId") == null ? "" : (String) map.get("payId");
                    String actualId = map.get("actualId") == null ? "" : (String) map.get("actualId");
//					String accountPid = map.get("accountPid") == null ? "" : (String)map.get("accountPid");
                    String payStatus = map.get("payStatus") == null ? "" : (String) map.get("payStatus");
                    String refundStatus = map.get("refundStatus") == null ? "" : (String) map.get("refundStatus");
                    Object ob = map.get("actualCost") == null ? "" : map.get("actualCost");
                    String actualCost = ob.toString();
                    if ("2".equals(payStatus)) {
                        if ("1".equals(refundStatus)) {
                            log.info("该笔订单正在退款中");
                            response.setStatus(510);
                            response.setMessage("该笔订单正在退款中");
                            return response;
                        } else if ("2".equals(refundStatus)) {
                            log.info("该笔订单已经退款成功了");
                            response.setStatus(511);
                            response.setMessage("该笔订单已经退款成功了");
                            return response;
                        } else {
                            if (StringUtils.isEmpty(actualCost) || new BigDecimal(actualCost).compareTo(BigDecimal.ZERO) == 0) {
                                log.info("当前退款金额为0，无需退款！");
                                if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                    accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "2", actualCost, aliRefundTO.getRefundReason(), null, aliRefundTO.getOutRequestNo());
                                } else {
                                    log.info("虚假请求");
                                    response.setStatus(502);
                                    response.setMessage("该订单是虚假订单！");
                                    return response;
                                }
                                response.setMessage("当前退款金额为0，无需退款！");
                                return response;
                            } else {
                                if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                    accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "1", null, aliRefundTO.getRefundReason(), null, aliRefundTO.getOutRequestNo());
                                } else {
                                    log.info("虚假请求");
                                    response.setStatus(503);
                                    response.setMessage("该订单是虚假订单！");
                                    return response;
                                }
//								AlipayClient client = new DefaultAlipayClient(AlipayConfig.UNIFIEDORDER_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
//										AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);

                                AlipayClient client = new DefaultAlipayClient(AlipayConfig.UNIFIEDORDER_URL, aliMap.get("appId"), aliMap.get("appRsa2"), AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                                        aliMap.get("appAliPublicKey"), AlipayConfig.SIGNTYPE);
                                AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();
                                AlipayTradeRefundModel model = new AlipayTradeRefundModel();
                                model.setOutTradeNo(aliRefundTO.getOutTradeNo());
                                model.setTradeNo(payId);
                                model.setRefundAmount(actualCost);
//								model.setRefundAmount("0.01");
                                model.setRefundReason(aliRefundTO.getRefundReason());
                                model.setOutRequestNo(aliRefundTO.getOutRequestNo());
                                model.setOperatorId("OP001");
                                model.setStoreId("NJ_S_001");
                                model.setTerminalId("NJ_T_001");

                                alipay_request.setBizModel(model);
                                try {
                                    AlipayTradeRefundResponse alipay_response = client.execute(alipay_request);
                                    log.info("调用支付宝退款返回结果>>>>>>>>>>>>>>>>" + JSON.toJSONString(alipay_response));
                                    if (alipay_response.isSuccess()) {
                                        log.info("调用支付宝退款成功");
                                        if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                            accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "2", actualCost, null, null, null);
                                        } else {
                                            log.info("虚假请求");
                                            response.setStatus(504);
                                            response.setMessage("该订单是虚假订单！");
                                            return response;
                                        }
                                        response.setMessage("退款成功");
                                        return response;
                                    } else {
                                        log.info("调用支付宝退款失败>>>>" + JSON.toJSONString(alipay_response));
                                        if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                            accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "3", null, null, alipay_response.getSubMsg(), null);
                                        } else {
                                            log.info("虚假请求");
                                            response.setStatus(505);
                                            response.setMessage("该订单是虚假订单！");
                                            return response;
                                        }
                                        response.setStatus(512);
                                        response.setMessage("退款失败");
                                        return response;
                                    }
                                } catch (Exception e) {
                                    log.error("支付宝退款错误！", e);
                                    if (aliRefundTO.getOutTradeNo().equals(actualId)) {
                                        accountBookMapper.updateRefundStatusByActualId(aliRefundTO.getOutTradeNo(), "3", null, null, "调用支付宝异常！", null);
                                    } else {
                                        log.info("虚假请求");
                                        response.setStatus(505);
                                        response.setMessage("该订单是虚假订单！");
                                        return response;
                                    }
                                    e.printStackTrace();
                                    response.setStatus(521);
                                    response.setMessage("调用支付宝异常！");
                                    return response;
                                }
                            }
                        }
                    } else {
                        log.info("该笔订单还未支付过");
                        response.setStatus(509);
                        response.setMessage("该笔订单还未支付过");
                        return response;
                    }
                } else {
                    response.setStatus(508);
                    response.setMessage("未查询到该笔订单的支付信息！");
                    return response;
                }
            } else {
                response.setStatus(507);
                response.setMessage("outTradeNo不能为空");
                return response;
            }
        } else {
            response.setStatus(506);
            response.setMessage("未获取到当前商户的支付宝信息");
            return response;
        }
    }

    public ObjectRestResponse refund(RefundParam refundParam) throws AlipayApiException {
        AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(refundParam.getOutTradeNo());
        model.setTradeNo(refundParam.getTradeNo());
        model.setRefundAmount(refundParam.getRefundMoney().toString());
        model.setRefundReason(refundParam.getRefundReason());
        model.setOutRequestNo(refundParam.getOutRequestNo());
        model.setOperatorId("OP001");
        model.setStoreId("NJ_S_001");
        model.setTerminalId("NJ_T_001");
        alipay_request.setBizModel(model);
        AlipayTradeRefundResponse alipayTradeRefundResponse = alipayClient.certificateExecute(alipay_request);

        ObjectRestResponse restResponse = new ObjectRestResponse();
        if (!alipayTradeRefundResponse.isSuccess()) {
            restResponse.setStatus(512);
            restResponse.setMessage(alipayTradeRefundResponse.getSubMsg());
        }
        return restResponse;

    }
}
