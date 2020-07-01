package com.github.wxiaoqi.pay.biz;

import com.gexin.fastjson.JSON;
import com.github.wxiaoqi.feign.CommodityFeign;
import com.github.wxiaoqi.pay.biz.support.NotifySuccessParam;
import com.github.wxiaoqi.pay.entity.BizAccountBook;
import com.github.wxiaoqi.pay.mapper.AccountBookMapper;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 支付回调处理本地业务，
 *
 * @author: guohao
 * @create: 2020-04-09 17:30
 **/
@Slf4j
@Component
public class NotifySuccessBiz {

    @Resource
    private AccountBookMapper accountBookMapper;
    @Resource
    private CommodityFeign commodityFeign;


    public void doNotifySuccessBusiness(NotifySuccessParam param) {
        log.info("支付回调处理本地业务，param:{}",JSON.toJSONString(param));
        BizAccountBook bizAccountBook = accountBookMapper.selectByActualId(param.getOutTradeNo());
        if(bizAccountBook == null){
            log.error("支付回调未找到支付账单。param:{}",JSON.toJSONString(param));
            return;
        }
        if (AceDictionary.PAY_STATUS_PAID.equals(bizAccountBook.getPayStatus())) {
            if (log.isWarnEnabled()) {
                log.warn("订单支付二次回调,param:{}", JSON.toJSONString(param));
            }
            return;
        }
        try {
            if(AceDictionary.BUS_TYPE_OLD.equals(bizAccountBook.getBusType())){
                //原业务
                doNotifySuccessBusinessOld(param,bizAccountBook);
            }else{
                doNotifySuccessBusinessV2(param,bizAccountBook);
            }
        }catch (Exception e){
            log.error("支付回调处理本地业务失败。param:{}, msg:{}",JSON.toJSONString(param),e.getMessage());

            BizAccountBook update = new BizAccountBook();
            update.setId(bizAccountBook.getId());
            String message = e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage();
            update.setPayFailRemark("支付回调处理本地业务失败: "+message);
            accountBookMapper.updateByPrimaryKeySelective(update);
        }

    }

    /**
     * 原支付逻辑
     * @Author guohao
     * @Date 2020/4/20 12:01
     */
    public void doNotifySuccessBusinessOld( NotifySuccessParam param,BizAccountBook bizAccountBook) {

        if(log.isInfoEnabled()){
            log.info("（旧业务）支付宝回调, param:{} ",JSON.toJSONString(param));
        }
        if (param.getOutTradeNo().equals(bizAccountBook.getAccountPid())) {
            //按accountPid维护payId
            accountBookMapper.updatePayIdAndAppIdByActualPid(param.getOutTradeNo(), param.getTradeNo(), param.getAppId());
        } else if (param.getOutTradeNo().equals(bizAccountBook.getActualId())) {
            //按actualId维护payId
            accountBookMapper.updatePayIdAndAppIdByActualId(param.getOutTradeNo(), param.getTradeNo(), param.getAppId());
        } else {
            if (log.isWarnEnabled()) {
                log.warn("虚假请求,param:{}", JSON.toJSONString(param));
            }
            return;
        }
        //TODO 这里属于项目的业务逻辑。应异步处理，打算后期使用mq
        ObjectRestResponse restResponse = doPayOrderFinish(param);
        if(log.isInfoEnabled()){
            log.info("（旧业务）支付回调 param:{}, 本地业务响应结果：{}",
                    JSON.toJSONString(param),JSON.toJSONString(restResponse));
        }
        if (200 != restResponse.getStatus()) {
            throw new BusinessException(restResponse.getMessage());
        }
    }

    /**
     * 新支付逻辑
     *
     * 1.验证支付金额是否准确，并更新支付状态
     * 2.具体业务处理，交给业务方提供的回调接口
     *
     * @Author guohao
     * @Date 2020/4/20 12:01
     */
    public void doNotifySuccessBusinessV2(NotifySuccessParam param,BizAccountBook accountBook) {

        if(log.isInfoEnabled()){
            log.info("（新业务）支付宝回调, param:{} ",JSON.toJSONString(param));
        }

        StringBuilder errorMsgBuilder = new StringBuilder();
        if(accountBook.getActualCost().compareTo(new BigDecimal(param.getTotalAmount())) != 0){
            log.error("支付回调金额与支付账单中金额不一致，param:{}, 账单金额：{}",JSON.toJSONString(param),accountBook.getActualCost());
            errorMsgBuilder.append("支付回调金额与支付账单中金额不一致. 支付金额:").append(param.getTotalAmount())
                    .append(";账单金额：").append(accountBook.getActualId()).append("actualId:").append(accountBook.getActualId());
        }
        BizAccountBook update = new BizAccountBook();
        update.setId(accountBook.getId());
        update.setPayType(param.getPayType());
        update.setAppId(param.getAppId());
        update.setPayId(param.getTradeNo());
        update.setPayDate(DateTimeUtil.getLocalTime());
        update.setModifyTime(DateTimeUtil.getLocalTime());
        update.setModifyBy("pay notify");
        if(errorMsgBuilder.length()>0){
            update.setPayFailRemark(errorMsgBuilder.toString());
            update.setPayStatus(AceDictionary.PAY_STATUS_FAIL);
        }else{
            update.setPayStatus(AceDictionary.PAY_STATUS_PAID);
        }
        accountBookMapper.updateByPrimaryKeySelective(update);
        //TODO 这里属于项目的业务逻辑。应异步处理，打算后期使用mq
        param.setSubId(accountBook.getSubId());
        ObjectRestResponse restResponse = doPayOrderFinish(param);
        if(log.isInfoEnabled()){
            log.info("（新业务）支付回调 param:{}, 本地业务响应结果：{}",
                    JSON.toJSONString(param),JSON.toJSONString(restResponse));
        }
        if (200 != restResponse.getStatus()) {
            throw new BusinessException(restResponse.getMessage());
        }
    }



    private ObjectRestResponse doPayOrderFinish(NotifySuccessParam param) {

        PayOrderFinishIn payOrderFinishIn = new PayOrderFinishIn();
        payOrderFinishIn.setPayType(param.getPayType());
        payOrderFinishIn.setActualId(param.getOutTradeNo());
        payOrderFinishIn.setPayId(param.getTradeNo());
        payOrderFinishIn.setTotalFee(param.getTotalAmount());
        payOrderFinishIn.setMchId(param.getMchId());
        payOrderFinishIn.setAppId(param.getAppId());
        return commodityFeign.doPayOrderFinish(payOrderFinishIn);
    }

}
