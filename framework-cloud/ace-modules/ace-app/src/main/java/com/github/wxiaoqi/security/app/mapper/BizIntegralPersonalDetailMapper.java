package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizIntegralPersonalDetail;
import com.github.wxiaoqi.security.app.vo.integral.IntegralPersonalVo;
import com.github.wxiaoqi.security.app.vo.integral.RuleInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 个人积分明细表
 * 
 * @author zxl
 * @Date 2018-12-28 10:17:42
 */
public interface BizIntegralPersonalDetailMapper extends CommonMapper<BizIntegralPersonalDetail> {

    /**
     * 查询用户个人积分
     * @param userId
     * @return
     */
    String selectUserIntegralInfo(String userId);

    /**
     * 查询用户积分账单
     * @param userId
     * @param createTime
     * @param page
     * @param limit
     * @return
     */
    List<IntegralPersonalVo> selectIntegralPersonalDetail(@Param("userId") String userId, @Param("createTime") String createTime,
                                                          @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 根据规则编码查询规则信息
     * @param ruleCode
     * @return
     */
    RuleInfo selectRuleInfoByCode(String ruleCode);

    /**
     * 查询用户积分明细
     * @param userId
     * @param ruleId
     * @return
     */
    int selectRuleInfoByUserId(@Param("userId") String userId, @Param("ruleId") String ruleId ,@Param("createTime") String createTime);

    /**
     * 查询小组的帖子是否领取
     * @param groupId
     * @param ruleId
     * @param objectId
     * @return
     */
    int selectGroupRuleInfoByUserId(@Param("groupId") String groupId, @Param("ruleId") String ruleId ,@Param("objectId") String objectId);


}
