package com.github.wxiaoqi.security.schedulewo.mapper;

import com.github.wxiaoqi.security.schedulewo.entity.BizFlowSkills;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 流程所需技能表
 * 
 * @author zxl
 * @Date 2018-12-05 10:24:31
 */
public interface BizFlowSkillsMapper extends CommonMapper<BizFlowSkills> {

    /**
     * 查询流程所需要的技能
     * @param flowId
     * @return
     */
    public List<BizFlowSkills> getSkillsByFlowId(String flowId);
}
