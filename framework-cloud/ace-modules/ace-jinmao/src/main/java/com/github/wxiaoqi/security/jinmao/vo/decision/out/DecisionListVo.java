package com.github.wxiaoqi.security.jinmao.vo.decision.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: guohao
 * @date: 2020-06-04 17:18
 **/
@Data
public class DecisionListVo  {

    private String id;

    private String projectId;

    //事件类型 1：一般事件 2：特殊事件
    private Integer eventType;

    //标题
    private String title;

    //内容
    private String content;

    //
    private Date startTime;

    //
    private Date endTime;

    //发布状态 0：未发布， 1：已发布
    private Integer publishStatus;

    //进度比例
    private BigDecimal progressRate;

    //
    private Date createTime;

    private Integer decisionStatus;

    public String getProgressRateDesc(){
        if(progressRate == null){
            return "0%";
        }
        return String.format("%s%%", progressRate.multiply(new BigDecimal("100")).setScale(2));
    }

    public String getProgressStatusDesc(){
        if(System.currentTimeMillis()< startTime.getTime()){
            return "未开始";
        }else if(System.currentTimeMillis() <= endTime.getTime()){
            return "进行中";
        }else{
            return "已结束";
        }
    }

    public String getDecisionStatusDesc(){
        if(AceDictionary.DECISION_STATUS_NONE.equals(decisionStatus)){
            return "";
        }
        return AceDictionary.DECISION_STATUS.get(decisionStatus);
    }

    public String getEventTypeDesc(){
        return AceDictionary.DECISION_EVENT_TYPE.getOrDefault(eventType,"未知事件");
    }

}
