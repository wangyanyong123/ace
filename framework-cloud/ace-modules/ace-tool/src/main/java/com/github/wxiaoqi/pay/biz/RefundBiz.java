package com.github.wxiaoqi.pay.biz;

import com.gexin.fastjson.JSON;
import com.github.wxiaoqi.pay.ali.service.AliPayService;
import com.github.wxiaoqi.pay.biz.support.RefundParam;
import com.github.wxiaoqi.pay.entity.BizAccountBook;
import com.github.wxiaoqi.pay.mapper.AccountBookMapper;
import com.github.wxiaoqi.pay.wechat.service.WeChatPayService;
import com.github.wxiaoqi.security.api.vo.to.ApplyRefundTO;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: guohao
 * @create: 2020-04-11 13:54
 **/
@Slf4j
@Component
public class RefundBiz {

    @Resource
    private AccountBookMapper accountBookMapper;
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private WeChatPayService weChatPayService;

    public ObjectRestResponse refund(ApplyRefundTO applyRefundTO) {
        if (log.isInfoEnabled()) {
            log.info("发起退款。param：{}", JSON.toJSONString(applyRefundTO));
        }
        ObjectRestResponse restResponse;
        try {

            BizAccountBook accountBook = accountBookMapper.selectByActualId(applyRefundTO.getOutTradeNo());
            restResponse = beforeRefund(applyRefundTO, accountBook);
            if (200 == restResponse.getStatus()) {
                RefundParam refundParam = buildRefundParam(applyRefundTO, accountBook);
                restResponse = doRefund(refundParam);
            }
        } catch (Exception e) {
            log.error("退款异常，param：{}", JSON.toJSONString(applyRefundTO), e);
            restResponse = new ObjectRestResponse();
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("退款异常。" + e.getMessage());
        }
        //回写退款状态
        if (200 != restResponse.getStatus()) {
            accountBookMapper.updateRefundStatusByActualId(applyRefundTO.getOutTradeNo(),
                    "3", null, null, restResponse.getMessage(), null);
        }
        if (log.isInfoEnabled()) {
            log.info("发起退款。response：{}", JSON.toJSONString(restResponse));
        }
        return restResponse;

    }

    private ObjectRestResponse doRefund(RefundParam refundParam) throws Exception {

        ObjectRestResponse restResponse = new ObjectRestResponse();
        if (AceDictionary.PAY_TYPE_ALIPAY.equals(refundParam.getPayType())) {
            restResponse = aliPayService.refund(refundParam);
        } else if (AceDictionary.PAY_TYPE_WECHAT.equals(refundParam.getPayType())) {
            restResponse = weChatPayService.refund(refundParam);
        } else if(AceDictionary.PAY_TYPE_ZERO.equals(refundParam.getPayType())){
            restResponse.setMessage("零元订单");
        }else{
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("非法的支付类型。" + refundParam.getPayType());
        }
        return restResponse;
    }

    /**
     * 退款前业务校验
     *
     * @param applyRefundTO : 请求入参
     * @param accountBook   :  支付信息
     * @return com.github.wxiaoqi.security.common.msg.ObjectRestResponse
     * @Author guohao
     * @Date 2020/4/11 16:01
     */
    private ObjectRestResponse beforeRefund(ApplyRefundTO applyRefundTO, BizAccountBook accountBook) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (accountBook == null ) {
            response.setStatus(508);
            response.setMessage("未查询到该笔订单的支付信息！");
            return response;
        }
        String payStatus = accountBook.getPayStatus();
        BigDecimal actualCost = accountBook.getActualCost();
        BigDecimal refundAmount = accountBook.getRefundAmount();
        if(refundAmount == null){
            refundAmount = BigDecimal.ZERO;
        }

        if (!"2".equals(payStatus)) {
            log.info("该笔订单还未支付过");
            response.setStatus(509);
            response.setMessage("该笔订单还未支付过");
            return response;
        }
        BigDecimal totalRefundPrice = refundAmount.add(applyRefundTO.getRefundMoney());
        if(totalRefundPrice.compareTo(actualCost) > 0){
            log.info("退款金额超过支付金额，param:{}",JSON.toJSONString(accountBook));
            response.setStatus(510);
            response.setMessage("退款金额超过支付金额");
            return response;
        }
        return response;

    }


    /**
     * 构建退款请求参数
     *
     * @param applyRefundTO : 请求入参
     * @param accountBook   : 支付信息
     * @return com.github.wxiaoqi.pay.biz.support.RefundParam
     * @Author guohao
     * @Date 2020/4/11 16:01
     */
    private RefundParam buildRefundParam(ApplyRefundTO applyRefundTO, BizAccountBook accountBook) {

        RefundParam refundParam = new RefundParam();
        BeanUtils.copyProperties(applyRefundTO, refundParam);

        String payId = accountBook.getPayId();
        String appId = accountBook.getAppId();
        BigDecimal paidMoney = accountBook.getActualCost();
        String payType = accountBook.getPayType();
        refundParam.setPayType(payType);
        //未传递退款金额，或者传递的金额为0时，为全部退款
        refundParam.setPaidMoney(paidMoney);
        if (refundParam.getRefundMoney() != null &&
                refundParam.getRefundMoney().compareTo(BigDecimal.ZERO) == 0) {
            refundParam.setRefundMoney(paidMoney);
        }
        refundParam.setAppId(appId);
        refundParam.setTradeNo(payId);
        return refundParam;
    }
}
