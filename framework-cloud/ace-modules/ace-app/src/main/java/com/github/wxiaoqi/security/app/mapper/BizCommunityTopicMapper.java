package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCommunityTopic;
import com.github.wxiaoqi.security.app.vo.topic.out.CommunityTopicInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社区话题表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizCommunityTopicMapper extends CommonMapper<BizCommunityTopic> {

    /**
     * 查询社区话题列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<CommunityTopicVo> selectCommunityTopicList(@Param("projectId") String projectId,@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     *
     * @param id
     * @param userId
     * @return
     */
    CommunityTopicInfo selectCommunityTopicInfo(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询社区话题点赞数
     * @param topicId
     * @return
     */
    int selectUpNumByTopicId(String topicId);

    /**
     * 查询用户
     * @param userId
     * @return
     */
    String selectUserNameByUserId(String userId);

    /**
     * 社区话题操作置顶,隐藏
     * @param status
     * @param id
     * @param userId
     * @return
     */
    int updateCommunityTopicStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);

    /**
     * 判断用户是否是运营人员
     * @param userId
     * @return
     */
    int selectIsOperationByUserId(String userId);
}
