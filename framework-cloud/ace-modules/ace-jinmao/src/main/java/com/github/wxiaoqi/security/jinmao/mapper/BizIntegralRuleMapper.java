package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizIntegralRule;
import com.github.wxiaoqi.security.jinmao.vo.rule.out.RuleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 个人积分规则配置表
 * 
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
public interface BizIntegralRuleMapper extends CommonMapper<BizIntegralRule> {

    /**
     *查询积分规则列表
     * @param ruleStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<RuleVo> selectRuleList(@Param("type")String type ,@Param("ruleStatus") String ruleStatus, @Param("searchVal") String searchVal,
                                @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询积分规则列表数量
     * @param ruleStatus
     * @param searchVal
     * @return
     */
    int selectRuleCount(@Param("type")String type ,@Param("ruleStatus") String ruleStatus, @Param("searchVal") String searchVal);

    /**
     * 积分规则操作(0-删除,2-已启用,3-已停用)
     * @param userId
     * @param status
     * @param id
     * @return
     */
    int updateRuleStatus(@Param("userId") String userId,@Param("status") String status,@Param("id") String id);


    /**
     * 查询积分规则详情
     * @param id
     * @return
     */
    RuleVo selectRuleInfo(String id);






}
