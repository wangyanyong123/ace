package com.github.wxiaoqi.security.app.vo.decision.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: guohao
 * @date: 2020-06-04 19:52
 **/
@Data
public class DecisionInfoVo {

    private String id;

    //项目id
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

    //
    private String remark;

    private List<DecisionAnnexInfo> annexList;

    private Integer decisionStatus;

    //进度比例
    private BigDecimal progressRate;

    //可投票次数
    private long canVoteCount;

    private BigDecimal personalRate;


    public Date getCurrentDate(){
        return new Date();
    }

    public Integer getProgressStatus(){
        if(System.currentTimeMillis()< startTime.getTime()){
            return AceDictionary.PROGRESS_STATUS_UN_START;
        }else if(System.currentTimeMillis() <= endTime.getTime()){
            return AceDictionary.PROGRESS_STATUS_START;
        }else{
            return AceDictionary.PROGRESS_STATUS_END;
        }
    }

    public String getPersonalRateDesc(){
       return String.format("%s%%", personalRate.multiply(new BigDecimal("100")).setScale(2));
    }

    public String getProgressRateDesc(){
       return String.format("%s%%", progressRate.multiply(new BigDecimal("100")).setScale(2));
    }

    public String getDecisionStatusDesc(){
        return AceDictionary.DECISION_STATUS.get(decisionStatus);
    }

    public String getEventTypeDesc(){
        return AceDictionary.DECISION_EVENT_TYPE.getOrDefault(eventType,"未知事件");
    }

}
