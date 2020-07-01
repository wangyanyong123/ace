package com.github.wxiaoqi.security.jinmao.vo.decision.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;

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

    public Integer getProgressStatus(){
        if(System.currentTimeMillis()< startTime.getTime()){
            return AceDictionary.PROGRESS_STATUS_UN_START;
        }else if(System.currentTimeMillis() <= endTime.getTime()){
            return AceDictionary.PROGRESS_STATUS_START;
        }else{
            return AceDictionary.PROGRESS_STATUS_END;
        }
    }

    public String getDecisionStatusDesc(){
        return AceDictionary.DECISION_STATUS.get(decisionStatus);
    }

    public String getEventTypeDesc(){
        return AceDictionary.DECISION_EVENT_TYPE.getOrDefault(eventType,"未知事件");
    }

}
