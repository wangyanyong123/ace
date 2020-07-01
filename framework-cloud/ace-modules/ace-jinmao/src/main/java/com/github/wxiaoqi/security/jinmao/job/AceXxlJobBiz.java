package com.github.wxiaoqi.security.jinmao.job;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.job.feign.AceXxlJobFeign;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.xxl.job.core.util.DateUtil;
import com.xxl.job.executor.CornUtil;
import com.xxl.job.executor.ExecutorRouteStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class AceXxlJobBiz {


    @Value("${xxl.job.executor.appname}")
    private String executorAppName;
    @Resource
    private AceXxlJobFeign aceXxlJobFeign;

    public void addDecisionEndJob(String decisionId,Date endTime){
        if(log.isInfoEnabled()){
            log.info("添加决策结束定时任务：decisionId:{}, emdTime:{}",decisionId, DateUtils.formatDateTime(endTime));
        }

        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroupName(executorAppName);
        xxlJobInfo.setJobDesc("决策结束定时任务");
        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.ROUND.name());
        String cronExpression = CornUtil.getCronExpression(endTime);
        xxlJobInfo.setJobCron(cronExpression);
        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN.name());
        xxlJobInfo.setExecutorHandler("decisionEndJobHandler");
        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name());
        xxlJobInfo.setExecutorFailRetryCount(3);
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setAuthor("system");
        xxlJobInfo.setAlarmEmail("guohao@sinochem.com");
        xxlJobInfo.setExecutorParam(decisionId);
        xxlJobInfo.setTriggerStatus(1);

        if(log.isInfoEnabled()){
            log.info("添加决策结束定时任务请求：decisionId={},param={}",decisionId,JSON.toJSONString(xxlJobInfo));
        }
        ReturnT<String> returnT = aceXxlJobFeign.add(xxlJobInfo);
        if(log.isInfoEnabled()){
            log.info("添加决策结束定时任务响应：decisionId={}，result={}",decisionId,returnT);
        }


    }
}
