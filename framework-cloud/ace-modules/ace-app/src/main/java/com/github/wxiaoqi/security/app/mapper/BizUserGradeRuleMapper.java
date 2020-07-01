package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizUserGradeRule;
import com.github.wxiaoqi.security.app.vo.intergral.out.SignInfo;
import com.github.wxiaoqi.security.app.vo.intergral.out.TaskVo;
import com.github.wxiaoqi.security.app.vo.intergral.out.UserGradeVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 运营服务-用户等级规则表
 * 
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
public interface BizUserGradeRuleMapper extends CommonMapper<BizUserGradeRule> {

    List<UserGradeVo> getGradeRule();

    List<TaskVo> getAllTask();

    List<SignInfo> getAllSignDay();

    int getNormalSign();
}
