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

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class AceXxlJobBiz {

    @Value("${order.pay-time-out}")
    private int orderPayTimeOut;

    @Value("${xxl.job.executor.appname}")
    private String executorAppName;
    @Resource
    private AceXxlJobFeign aceXxlJobFeign;

    public void addProductOrderPayTimeoutJob(String orderId){
        if(log.isInfoEnabled()){
            log.info("添加商品订单支付超时定时任务：orderId:{}",orderId);
        }

        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroupName(executorAppName);
        xxlJobInfo.setJobDesc("商品订单支付超时");
        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.ROUND.name());
        Date date = DateUtil.addMinutes(new Date(), orderPayTimeOut);
        String cronExpression = CornUtil.getCronExpression(date);
        xxlJobInfo.setJobCron(cronExpression);
        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN.name());
        xxlJobInfo.setExecutorHandler("productOrderPayTimeOutJobHandler");
        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name());
        xxlJobInfo.setExecutorFailRetryCount(3);
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setAuthor("system");
        xxlJobInfo.setAlarmEmail("guohao@sinochem.com");
        xxlJobInfo.setExecutorParam(orderId);
        xxlJobInfo.setTriggerStatus(1);

        if(log.isInfoEnabled()){
            log.info("添加商品订单支付超时定时任务请求：orderId={},param={}",orderId,JSON.toJSONString(xxlJobInfo));
        }
        ReturnT<String> returnT = aceXxlJobFeign.add(xxlJobInfo);
        if(log.isInfoEnabled()){
            log.info("添加商品订单支付超时定时任务相应：orderId={}，result={}",orderId,returnT);
        }


    }
}
