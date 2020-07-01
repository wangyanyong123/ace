package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizDecisionAnnex;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 决策附件表
 * 
 * @author guohao
 * @Date 2020-06-04 21:29:07
 */
public interface BizDecisionAnnexMapper extends CommonMapper<BizDecisionAnnex> {

    List<BizDecisionAnnex> selectByDecisionId(String decisionId);
}
