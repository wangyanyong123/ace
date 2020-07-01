package com.github.wxiaoqi.security.app.job;

import com.github.wxiaoqi.security.app.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法",
 * destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Slf4j
@Component
public class ProductOrderPayTimeOutJobHandler {

    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;

    /**
     * 1、订单超时（Bean模式）
     */
    @XxlJob("productOrderPayTimeOutJobHandler")
    public ReturnT<String> orderPayTimeOutJobHandler(String param) {
        if(log.isInfoEnabled()){
            log.info("执行商品订单支付超时任务 开始， orderId:{}",param);
        }
        XxlJobLogger.log("XXL-JOB, handler={}, param={}",
                "productOrderPayTimeOutJobHandler",param);
        if(StringUtils.isEmpty(param)){
            XxlJobLogger.log("参数为空");
            return ReturnT.FAIL;
        }

        BizProductOrder productOrder = bizProductOrderBiz.selectById(param);
        if(productOrder == null){
            XxlJobLogger.log("单据不存在，param={}",param);
            log.info("执行商品订单支付超时任务，单据不存在， orderId:{}",param);
            return ReturnT.SUCCESS;
        }
        if(AceDictionary.ORDER_STATUS_W_PAY.equals(productOrder.getOrderStatus())){
            ObjectRestResponse restResponse = bizProductOrderBiz.cancelOrder(param);
            if(!restResponse.success()){
                log.error("执行商品订单支付超时任务 失败， orderId:{}， message:{}",param,restResponse.getMessage());
                XxlJobLogger.log("取消订单失败，param={}，message={}",param,restResponse.getMessage());
                return ReturnT.FAIL;
            }
        }else{
            XxlJobLogger.log("单据非待支付状态，orderStatus={}"
                    ,AceDictionary.ORDER_STATUS.get(productOrder.getOrderStatus()));
        }

        if(log.isInfoEnabled()){
            log.info("执行商品订单支付超时任务 结束， orderId:{}",param);
        }
        return ReturnT.SUCCESS;
    }

}
