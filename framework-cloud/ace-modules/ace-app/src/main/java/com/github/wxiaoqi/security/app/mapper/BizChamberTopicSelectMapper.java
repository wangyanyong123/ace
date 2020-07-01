package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizChamberTopicSelect;
import com.github.wxiaoqi.security.app.vo.topic.out.SelContentVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 议事厅话题投票选项表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizChamberTopicSelectMapper extends CommonMapper<BizChamberTopicSelect> {

    /**
     * 查询选项列表
     * @param topicId
     * @param userId
     * @return
     */
    List<SelContentVo> selectBallotSelectList(@Param("topicId") String topicId, @Param("userId") String userId);

    /**
     * 查询每个投票选项的人数
     * @param selectId
     * @param topicId
     * @return
     */
    int selectSelNumById(@Param("selectId") String selectId, @Param("topicId") String topicId);
}
