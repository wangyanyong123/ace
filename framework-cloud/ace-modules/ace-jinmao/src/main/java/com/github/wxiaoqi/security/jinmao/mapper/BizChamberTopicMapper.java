package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopic;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 议事厅话题表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizChamberTopicMapper extends CommonMapper<BizChamberTopic> {

    /**
     * 查询议事厅话题列表
     * @param projectId
     * @param tagId
     * @param topicType
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ChamberTopicVo> selectChamberTopicList(@Param("projectId") String projectId, @Param("tagId") String tagId,
                                                @Param("topicType") String topicType, @Param("showType") String showType,
                                                @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                                @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询议事厅话题列表数量
     * @param projectId
     * @param tagId
     * @param topicType
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    int selectChamberTopicCount(@Param("projectId") String projectId, @Param("tagId") String tagId,
                                @Param("topicType") String topicType, @Param("showType") String showType,
                                @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                @Param("searchVal") String searchVal);

    /**
     * 查询议事厅话题详情
     * @param topicId
     * @return
     */
    ChamberTopicInfo selectChamberTopicInfo(@Param("topicId") String topicId);


    /**
     * 议事厅话题操作置顶,隐藏
     * @param status
     * @param id
     * @param userId
     * @return
     */
    int updateChamberTopicStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);
	
}
