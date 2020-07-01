package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.job.feign.AceXxlJobFeign;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.xxl.job.core.util.DateUtil;
import com.xxl.job.executor.CornUtil;
import com.xxl.job.executor.ExecutorRouteStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class ReservationOrderTimeOutJobBiz {

    @Value("${order.pay-time-out}")
    private int orderPayTimeOut;

    @Value("${xxl.job.executor.appname}")
    private String executorAppName;
    @Autowired
    private AceXxlJobFeign aceXxlJobFegin;

    public void addOrderPayTimeoutJob(String orderId){
        if(log.isInfoEnabled()){
            log.info("添加服务订单超时定时任务：orderId:{}",orderId);
        }

        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroupName(executorAppName);
        xxlJobInfo.setJobDesc("服务订单支付超时");
        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.ROUND.name());
        Date date = DateUtil.addMinutes(new Date(), orderPayTimeOut);
        String cronExpression = CornUtil.getCronExpression(date);
        xxlJobInfo.setJobCron(cronExpression);
        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN.name());
        xxlJobInfo.setExecutorHandler("reservationOrderPayTimeOutJobHandler");
        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name());
        xxlJobInfo.setExecutorFailRetryCount(3);
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setAuthor("system");
        xxlJobInfo.setAlarmEmail("wangyanyong@sinochem.com");
        xxlJobInfo.setExecutorParam(orderId);
        xxlJobInfo.setTriggerStatus(1);

        if(log.isInfoEnabled()){
            log.info("添加服务订单超时定时任务请求：orderId={},param={}",orderId,JSON.toJSONString(xxlJobInfo));
        }
        ReturnT<String> returnT = aceXxlJobFegin.add(xxlJobInfo);
        if(log.isInfoEnabled()){
            log.info("添加服务订单超时定时任务响应：orderId={}，result={}",orderId,returnT);
        }


    }
}
