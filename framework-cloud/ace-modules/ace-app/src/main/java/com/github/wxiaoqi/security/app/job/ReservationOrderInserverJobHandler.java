package com.github.wxiaoqi.security.app.job;

import com.github.wxiaoqi.security.app.biz.BizReservationOrderBiz;
import com.github.wxiaoqi.security.app.entity.BizReservationOrder;
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
public class ReservationOrderInserverJobHandler {

    @Autowired
    private BizReservationOrderBiz bizReservationOrderBiz;

    /**
     * 1、订单超时（Bean模式）
     */
    @XxlJob("reservationOrderPayTimeOutJobHandler")
    public ReturnT<String> orderPayTimeOutJobHandler(String param) {
        XxlJobLogger.log("XXL-JOB, handler={}, param={}",
                "reservationOrderPayTimeOutJobHandler",param);
        if(StringUtils.isEmpty(param)){
            XxlJobLogger.log("参数为空");
            return ReturnT.FAIL;
        }

        BizReservationOrder reservationOrder = bizReservationOrderBiz.selectById(param);
        if(reservationOrder == null){
            XxlJobLogger.log("单据不存在，param={}",param);
            return ReturnT.FAIL;
        }
        if(AceDictionary.ORDER_STATUS_W_PAY.equals(reservationOrder.getOrderStatus())){
            ObjectRestResponse restResponse = bizReservationOrderBiz.cancel(param);
            if(!restResponse.success()){
                XxlJobLogger.log("取消订单失败，param={}，message={}",param,restResponse.getMessage());
                return ReturnT.FAIL;
            }
        }else{
            XxlJobLogger.log("单据非待支付状态，orderStatus={}"
                    ,AceDictionary.ORDER_STATUS.get(reservationOrder.getOrderStatus()));
        }
        return ReturnT.SUCCESS;
    }

}
