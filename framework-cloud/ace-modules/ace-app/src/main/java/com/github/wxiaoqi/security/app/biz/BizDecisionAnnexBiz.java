package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.vo.decision.out.DecisionAnnexInfo;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizDecisionAnnex;
import com.github.wxiaoqi.security.app.mapper.BizDecisionAnnexMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 决策附件表
 *
 * @author guohao
 * @Date 2020-06-04 21:29:07
 */
@Service
public class BizDecisionAnnexBiz extends BusinessBiz<BizDecisionAnnexMapper,BizDecisionAnnex> {
    public List<DecisionAnnexInfo> findInfoListByDecisionId(String decisionId) {
        List<BizDecisionAnnex> list = this.mapper.selectByDecisionId(decisionId);
        return list.stream().map(item->{
            DecisionAnnexInfo annexInfo = new DecisionAnnexInfo();
            annexInfo.setName(item.getName());
            annexInfo.setUrl(item.getUrl());
            return annexInfo;
        }).collect(Collectors.toList());
    }
}