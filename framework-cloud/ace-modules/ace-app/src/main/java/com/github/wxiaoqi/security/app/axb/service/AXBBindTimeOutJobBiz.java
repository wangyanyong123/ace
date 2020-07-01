package com.github.wxiaoqi.security.app.axb.service;

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
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class AXBBindTimeOutJobBiz {

    @Value("${pns.expiration}")
    private Integer expiration;

    @Value("${xxl.job.executor.appname}")
    private String executorAppName;
    @Resource
    private AceXxlJobFeign aceXxlJobFegin;

    public void addBindTimeoutJob(String bindId,Integer timeOut){
        if(log.isInfoEnabled()){
            log.info("添加隐私号绑定超时定时任务：bindId:{}",bindId);
        }

        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroupName(executorAppName);
        xxlJobInfo.setJobDesc("隐私号绑定超时");
        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.ROUND.name());
        if(ObjectUtils.isEmpty(timeOut)){
            timeOut = expiration/60;
        }
        Date date = DateUtil.addMinutes(new Date(), timeOut);
        String cronExpression = CornUtil.getCronExpression(date);
        xxlJobInfo.setJobCron(cronExpression);
        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN.name());
        xxlJobInfo.setExecutorHandler("axbBindTimeOutInserverJobHandler");
        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name());
        xxlJobInfo.setExecutorFailRetryCount(3);
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setAuthor("system");
        xxlJobInfo.setAlarmEmail("wangyanyong@sinochem.com");
        xxlJobInfo.setExecutorParam(bindId);
        xxlJobInfo.setTriggerStatus(1);

        if(log.isInfoEnabled()){
            log.info("添加隐私号绑定超时定时任务请求：bindId={},param={}",bindId,JSON.toJSONString(xxlJobInfo));
        }
        ReturnT<String> returnT = aceXxlJobFegin.add(xxlJobInfo);
        if(log.isInfoEnabled()){
            log.info("添加隐私号绑定超时定时任务响应：bindId={}，result={}",bindId,returnT);
        }


    }
}
