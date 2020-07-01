package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizChamberTopic;
import com.github.wxiaoqi.security.app.vo.topic.out.ChamberTopicInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.ChamberTopicVo;
import com.github.wxiaoqi.security.app.vo.topic.out.TagVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
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
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<ChamberTopicVo> selectChamberTopicList(@Param("projectId") String projectId, @Param("userId") String userId, @Param("myUserId") String myUserId,
                                                @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询我参与的议事厅话题
     * @param projectId
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<ChamberTopicVo> selectMyplayerTopicList(@Param("projectId") String projectId, @Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询议事厅话题标签列表
     * @return
     */
    List<TagVo> selectTagList(String projectId);

    /**
     * 查询议事厅话题详情
     * @param id
     * @param userId
     * @return
     */
    ChamberTopicInfo selectChamberTopicInfo(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询社区话题点赞数
     * @param topicId
     * @return
     */
    int selectUpNumByTopicId(String topicId);

    /**
     * 议事厅话题操作置顶,隐藏
     * @param status
     * @param id
     * @param userId
     * @return
     */
    int updateChamberTopicStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);


}
