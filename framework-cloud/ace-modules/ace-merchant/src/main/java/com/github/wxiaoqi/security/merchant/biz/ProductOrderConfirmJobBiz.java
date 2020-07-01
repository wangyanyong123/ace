package com.github.wxiaoqi.security.merchant.biz;

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
public class ProductOrderConfirmJobBiz {

    @Value("${order.confirm-time}")
    private int orderConfirmTime;

    @Value("${xxl.job.executor.appname}")
    private String executorAppName;
    @Autowired
    private AceXxlJobFeign aceXxlJobFeign;

    public void addOrderConfirmJob(String orderId){
        if(log.isInfoEnabled()){
            log.info("添加超时确认收货定时任务：orderId:{}",orderId);
        }

        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroupName(executorAppName);
        xxlJobInfo.setJobDesc("超时自动确认收货");
        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.ROUND.name());
        Date date = DateUtil.addMinutes(new Date(), orderConfirmTime);
        String cronExpression = CornUtil.getCronExpression(date);
        xxlJobInfo.setJobCron(cronExpression);
        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN.name());
        xxlJobInfo.setExecutorHandler("productOrderConfirmJobHandler");
        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name());
        xxlJobInfo.setExecutorFailRetryCount(3);
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setAuthor("system");
        xxlJobInfo.setAlarmEmail("wangyanyong@sinochem.com");
        xxlJobInfo.setExecutorParam(orderId);
        xxlJobInfo.setTriggerStatus(1);

        if(log.isInfoEnabled()){
            log.info("添加超时确认收货定时任务请求：orderId={},param={}",orderId,JSON.toJSONString(xxlJobInfo));
        }
        ReturnT<String> returnT = aceXxlJobFeign.add(xxlJobInfo);
        if(log.isInfoEnabled()){
            log.info("添加超时确认收货定时任务响应：orderId={}，result={}",orderId,returnT);
        }


    }
}
