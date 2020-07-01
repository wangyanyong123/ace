package com.github.wxiaoqi.security.merchant.job;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.merchant.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.merchant.entity.BizProductOrder;
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
public class ProductOrderConfirmJobHandler {

    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;

    /**
     * 1、到时确认收货（Bean模式）
     */
    @XxlJob("productOrderConfirmJobHandler")
    public ReturnT<String> orderConfirmJobHandler(String param) {
        XxlJobLogger.log("XXL-JOB, handler={}, param={}",
                "productOrderConfirmJobHandler",param);
        if(StringUtils.isEmpty(param)){
            XxlJobLogger.log("参数为空");
            return ReturnT.FAIL;
        }

        BizProductOrder productOrder = bizProductOrderBiz.selectById(param);
        if(productOrder == null){
            XxlJobLogger.log("单据不存在，param={}",param);
            return ReturnT.FAIL;
        }
        if(AceDictionary.ORDER_STATUS_W_SIGN.equals(productOrder.getOrderStatus())){
            ObjectRestResponse restResponse = bizProductOrderBiz.confirmOrder(param);
            if(!restResponse.success()){
                XxlJobLogger.log("确认收货失败，param={}，message={}",param,restResponse.getMessage());
                return ReturnT.FAIL;
            }
        }else{
            XxlJobLogger.log("单据非待收货状态，orderStatus={}"
                    ,AceDictionary.ORDER_STATUS.get(productOrder.getOrderStatus()));
        }
        return ReturnT.SUCCESS;
    }

}
