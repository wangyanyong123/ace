package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopicVote;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 议事厅话题用户投票表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizChamberTopicVoteMapper extends CommonMapper<BizChamberTopicVote> {

    /**
     * 查询每个投票选项的人数
     * @param selectId
     * @param topicId
     * @return
     */
    int selectSelNumById(@Param("selectId") String selectId, @Param("topicId") String topicId);
}
