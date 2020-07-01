package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizDecision;
import com.github.wxiaoqi.security.app.vo.decision.in.QueryDecisionRequest;
import com.github.wxiaoqi.security.app.vo.decision.out.DecisionListVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 决策表
 * 
 * @author guohao
 * @Date 2020-06-04 21:29:06
 */
public interface BizDecisionMapper extends CommonMapper<BizDecision> {

    int additionProgressRate(@Param("id") String id,@Param("isPass")boolean isPass, @Param("progressRate") BigDecimal progressRate, @Param("modifyBy") String modifyBy);

    List<DecisionListVo> selectList(QueryDecisionRequest request);
}
