package com.github.wxiaoqi.security.schedulewo.mapper;

import com.github.wxiaoqi.security.schedulewo.entity.BizFlowDispatchRule;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.schedulewo.entity.BizFlowSkills;
import com.github.wxiaoqi.security.schedulewo.vo.BusDispatchRule;

import java.util.List;

/**
 * 流程调度规则
 * 
 * @author zxl
 * @Date 2018-12-05 10:53:46
 */
public interface BizFlowDispatchRuleMapper extends CommonMapper<BizFlowDispatchRule> {
    /**
     * 查询流程的调度规则
     * @param flowId
     * @return
     */
    public BusDispatchRule getDispatchRuleByFlowId(String flowId);
}
