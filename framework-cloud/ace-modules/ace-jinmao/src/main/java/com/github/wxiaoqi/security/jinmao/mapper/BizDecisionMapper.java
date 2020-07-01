package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizDecision;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.decision.in.QueryDecisionRequest;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionListVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 决策表
 * 
 * @author guohao
 * @Date 2020-06-04 13:33:19
 */
public interface BizDecisionMapper extends CommonMapper<BizDecision> {

    int countList(QueryDecisionRequest request);

    List<DecisionListVo> selectList(QueryDecisionRequest request);

    void deleteById(@Param("id") String id, @Param("remark") String remark,@Param("handler") String handler
            , @Param("deleteTime") Date deleteTime);
}
