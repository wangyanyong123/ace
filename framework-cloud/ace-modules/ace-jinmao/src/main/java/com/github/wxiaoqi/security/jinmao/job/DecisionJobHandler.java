package com.github.wxiaoqi.security.jinmao.job;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.decision.DecisionHandler;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizDecisionBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizDecision;
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
 */
@Slf4j
@Component
public class DecisionJobHandler {


    @Autowired
    private BizDecisionBiz decisionBiz;
    @Autowired
    private DecisionHandler decisionHandler;

    /**
     * 1、决策结束定时任务（Bean模式）
     *
     * 更新决策状态
     */
    @XxlJob("decisionEndJobHandler")
    public ReturnT<String> decisionEndJobHandler(String decisionId) {
        if(log.isInfoEnabled()){
            log.info("执行决策结束定时任务 开始， decisionId:{}",decisionId);
        }
        XxlJobLogger.log("XXL-JOB, handler={}, decisionId={}",
                "decisionEndJobHandler",decisionId);
        if(StringUtils.isEmpty(decisionId)){
            XxlJobLogger.log("参数为空");
            return ReturnT.FAIL;
        }

        BizDecision decision = decisionBiz.selectById(decisionId);
        if(decision == null){
            XxlJobLogger.log("决策不存在，decisionId={}",decisionId);
            log.info("执行决策结束定时任务，决策不存在， decisionId:{}",decisionId);
            return ReturnT.SUCCESS;
        }
        if(System.currentTimeMillis() >= decision.getEndTime().getTime()){
            if(AceDictionary.DECISION_STATUS_NONE.equals(decision.getDecisionStatus())){
                //投票中
                boolean pass = decisionHandler.isPass(decision.getEventType(), decision.getProgressRate());
                BizDecision update = new BizDecision();
                update.setId(decision.getId());
                Integer decisionStatus = pass?AceDictionary.DECISION_STATUS_PASS:AceDictionary.DECISION_STATUS_UN_PASS;
                update.setDecisionStatus(decisionStatus);
                update.setModifyBy("decisionEndJobHandler");

                decisionBiz.updateSelectiveById(update);
            }
        }else{
            XxlJobLogger.log("决策尚未结束，decisionId={},endTime ={}"
                    ,decisionId, DateUtils.formatDateTime(decision.getEndTime()));
        }

        if(log.isInfoEnabled()){
            log.info("执行决策结束定时任务 结束， decisionId:{}",decisionId);
        }
        return ReturnT.SUCCESS;
    }

}
