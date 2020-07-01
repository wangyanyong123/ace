package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCommunityTopic;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.CommentVo;
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
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<CommunityTopicVo> selectCommunityTopicList(@Param("projectId") String projectId, @Param("showType") String showType,
                                                    @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                                    @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);

    List<CommunityTopicVo> selectNewCommunityTopicList(@Param("projectId") String projectId, @Param("showType") String showType,
                                                    @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                                    @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询社区话题列表数量
     * @param projectId
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    int selectCommunityTopicCount(@Param("projectId") String projectId, @Param("showType") String showType,
                                  @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                  @Param("searchVal") String searchVal);


    /**
     * 查询社区话题详情
     * @param id
     * @return
     */
    CommunityTopicInfo selectCommunityTopicInfo(String id);

    /**
     * 查询用户
     * @param userId
     * @return
     */
    String selectUserNameByUserId(String userId);

    /**
     * 社区话题操作置顶,隐藏
     * @param status (0-隐藏,1-显示,3-置顶,4-取消置顶)
     * @param id
     * @param userId
     * @return
     */
    int updateCommunityTopicStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);

    /**
     * 查询评论列表
     * @param objectId
     * @param page
     * @param limit
     * @return
     */
    List<CommentVo> selectComunityTopicCommentList(@Param("objectId") String objectId, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询评论列表数量
     * @param objectId
     * @return
     */
    int selectComunityTopicCommentCount(@Param("objectId") String objectId);

}
