package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizDecisionVote;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * 决策投票表
 * 
 * @author guohao
 * @Date 2020-06-04 21:29:07
 */
public interface BizDecisionVoteMapper extends CommonMapper<BizDecisionVote> {

    boolean existByHouseId(@Param("decisionId") String decisionId, @Param("houseId") String houseId);
}
