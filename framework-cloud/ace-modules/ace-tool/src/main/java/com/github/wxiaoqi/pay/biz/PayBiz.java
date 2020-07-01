package com.github.wxiaoqi.pay.biz;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.pay.ali.service.AliPayService;
import com.github.wxiaoqi.pay.entity.BizAccountBook;
import com.github.wxiaoqi.pay.mapper.AccountBookMapper;
import com.github.wxiaoqi.pay.mapper.BizUserWechatMapper;
import com.github.wxiaoqi.pay.wechat.biz.WechatPayProxyFactory;
import com.github.wxiaoqi.pay.wechat.service.WeChatPayService;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author: guohao
 * @create: 2020-04-14 12:21
 **/
@Slf4j
@Component
public class PayBiz {

    @Autowired
    private WechatPayProxyFactory wechatPayProxyFactory;
    @Resource
    private BizUserWechatMapper bizUserWechatMapper;

    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private WeChatPayService weChatPayService;
    @Resource
    private AccountBookMapper accountBookMapper;

    public ObjectRestResponse pay(GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        if (log.isInfoEnabled()) {
            log.info("支付请求：{}", JSON.toJSONString(generateUnifiedOrderIn));
        }
        ObjectRestResponse restResponse = businessCheck(generateUnifiedOrderIn);
        if (!restResponse.success()) {
            if (log.isWarnEnabled()) {
                log.warn("支付失败，param:{},message:{}", JSON.toJSONString(generateUnifiedOrderIn), restResponse.getMessage());
            }
            return restResponse;
        }
        if (AceDictionary.PAY_TYPE_ALIPAY.equals(generateUnifiedOrderIn.getPayType())) {
            restResponse = aliPayService.aliAppPay(generateUnifiedOrderIn);
        } else {
            initOpenId(generateUnifiedOrderIn);
            restResponse = weChatPayService.pay(generateUnifiedOrderIn);
        }
        if (log.isInfoEnabled()) {
            log.info("支付响应：{}", JSON.toJSONString(restResponse));
        }
        return restResponse;
    }

    private ObjectRestResponse businessCheck(GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        ObjectRestResponse objectRestResponse ;
        int busType = accountBookMapper.getBusTypeByActualId(generateUnifiedOrderIn.getActualId());

        if(AceDictionary.BUS_TYPE_OLD.equals(busType)){
            objectRestResponse = doOldBusinessCheck(generateUnifiedOrderIn);
        }else{
            objectRestResponse= doNewBusinessCheck(generateUnifiedOrderIn);
        }
        return objectRestResponse;

    }

    private ObjectRestResponse doNewBusinessCheck(GenerateUnifiedOrderIn generateUnifiedOrderIn){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        BizAccountBook accountBook = accountBookMapper.selectBySubId(generateUnifiedOrderIn.getSubId());
        if(accountBook == null){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("单据不存在");
            objectRestResponse.setData(generateUnifiedOrderIn.getSubId());
            return objectRestResponse;
        }

        if (accountBook.getActualCost().compareTo(generateUnifiedOrderIn.getActualPrice()) != 0) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单金额有误");
            objectRestResponse.setData(accountBook.getActualCost());
            return objectRestResponse;
        }
        if (!AceDictionary.PAY_STATUS_UN_PAID.equals(accountBook.getPayStatus())) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("请确认单据支付状态。");
            objectRestResponse.setData(accountBook.getPayStatus());
        }
//        if(StringUtils.isNotEmpty(accountBook.getPayType())
//                && AceDictionary.PAY_TYPE.equals(generateUnifiedOrderIn.getPayType())){
//            //微信 第二次发起支付，支付id重复问题
//            String actualId = UUIDUtils.generateUuid();
//            BizAccountBook update = new BizAccountBook();
//            update.setId(accountBook.getId());
//            update.setActualId(actualId);
//            update.setModifyBy("pay second");
//            accountBookMapper.updateByPrimaryKeySelective(update);
//            generateUnifiedOrderIn.setActualId(actualId);
//        }
        return objectRestResponse;
    }
    private ObjectRestResponse doOldBusinessCheck(GenerateUnifiedOrderIn generateUnifiedOrderIn){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        Map<String, Object> bookStatusInfo = accountBookMapper.getAccountBookStatusInfoBySubId(generateUnifiedOrderIn.getSubId());
        if (bookStatusInfo == null || bookStatusInfo.size()==0) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("单据不存在");
            objectRestResponse.setData(generateUnifiedOrderIn.getSubId());
            return objectRestResponse;
        }
        BigDecimal actualCost = (BigDecimal) bookStatusInfo.get("actualCost");
        String payStatus = (String) bookStatusInfo.get("payStatus");
        String subscribeStatus = (String) bookStatusInfo.get("subscribeStatus");

        if (!"2".equals(subscribeStatus)) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("请确认单据状态。");
            objectRestResponse.setData(subscribeStatus);
            return objectRestResponse;
        }

        if (actualCost.compareTo(generateUnifiedOrderIn.getActualPrice()) != 0) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单金额有误");
            objectRestResponse.setData(actualCost);
            return objectRestResponse;
        }
        if (!"1".equals(payStatus)) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("请确认单据支付状态。");
            objectRestResponse.setData(payStatus);
        }
        return objectRestResponse;
    }

    private void initOpenId(GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        if (AceDictionary.PAY_TYPE_WECHAT.equals(generateUnifiedOrderIn.getPayType())) {
            if (StringUtils.isEmpty(generateUnifiedOrderIn.getOpenId())) {
                WeixinPayProxy weixinPayProxy = wechatPayProxyFactory.getWeixinPayProxyByAppType(generateUnifiedOrderIn.getAppType());
                String appId = weixinPayProxy.getWeixinPayAccount().getId();
                String userId = BaseContextHandler.getUserID();
                String openId = bizUserWechatMapper.getOpenId(appId, userId);
                generateUnifiedOrderIn.setOpenId(openId);
            }
        }
    }
}
