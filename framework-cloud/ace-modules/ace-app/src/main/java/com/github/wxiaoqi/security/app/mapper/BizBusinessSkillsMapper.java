package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizBusinessSkills;
import com.github.wxiaoqi.security.app.entity.BizFlowSkills;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;
import java.util.Map;

/**
 * 业务技能表
 * 
 * @author huangxl
 * @Date 2019-01-04 14:31:19
 */
public interface BizBusinessSkillsMapper extends CommonMapper<BizBusinessSkills> {
    List<BizBusinessSkills> selectBusSkillsList(Map<?, ?> map);
}
