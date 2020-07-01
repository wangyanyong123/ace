package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizDecisionVote;
import com.github.wxiaoqi.security.app.mapper.BizDecisionVoteMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 决策投票表
 *
 * @author guohao
 * @Date 2020-06-04 21:29:07
 */
@Service
public class BizDecisionVoteBiz extends BusinessBiz<BizDecisionVoteMapper,BizDecisionVote> {


    public boolean existByHouseId(String decisionId,String houseId) {
        return this.mapper.existByHouseId(decisionId,houseId);
    }
}