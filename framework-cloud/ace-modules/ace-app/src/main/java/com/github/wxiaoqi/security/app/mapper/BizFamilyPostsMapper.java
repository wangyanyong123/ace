package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizFamilyPosts;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsVo;
import com.github.wxiaoqi.security.app.vo.topic.out.GradeRuleVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 家里人帖子表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizFamilyPostsMapper extends CommonMapper<BizFamilyPosts> {


    /**
     * 查询家里人帖子列表
     * @param projectId
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<FamilyPostsVo> selectFamilyPostsList(@Param("projectId") String projectId, @Param("type") String type,@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 老版的家里人列表
     * @param projectId
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<FamilyPostsVo> selectOldFamilyPostsList(@Param("projectId") String projectId, @Param("type") String type,@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询项目下的帖子活动
     * @param projectId
     * @param userId
     * @param friendId
     * @param page
     * @param limit
     * @return
     */
    List<FamilyPostsVo> selectAllPostsList(@Param("projectId") String projectId, @Param("userId") String userId, @Param("friendId") List<String> friendId,
                                           @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询用户加入的黑名单用户
     * @param userId
     * @return
     */
    List<String> selectFriendIdByUser(String userId);


    List<String> selectUserIdByfriend(String userId);

    /**
     *
     * @param id
     * @param userId
     * @return
     */
    FamilyPostsInfo selectFamilyPostsInfo(@Param("id") String id, @Param("userId") String userId);


    /**
     * 查询社区话题点赞数
     * @param topicId
     * @return
     */
    int selectUpNumByTopicId(String topicId);



    /**
     * 家里人帖子操作置顶,隐藏
     * @param status
     * @param id
     * @param userId
     * @return
     */
    int updateFamilyPostsStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);


    /**
     * 查询用户的签到积分
     * @param userId
     * @return
     */
    String selectUserPointById(String userId);

    /**
     * 查询用户的个人积分值
     * @param userId
     * @return
     */
    String selectUserValueById(String userId);

    /**
     * 查询等级规则
     * @return
     */
    List<GradeRuleVo> selectGradeRuleList();



}
