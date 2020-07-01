package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizIntegralGroupDetail;
import com.github.wxiaoqi.security.app.vo.integral.PostsViewInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组积分明细表
 * 
 * @author zxl
 * @Date 2018-12-28 10:17:42
 */
public interface BizIntegralGroupDetailMapper extends CommonMapper<BizIntegralGroupDetail> {


       int selectGroupIntegralById(String groupId);

    /**
     * 查询帖子浏览量
     * @param groupId
     * @return
     */
    List<PostsViewInfo> selectPostsViewNum(String groupId);

    /**
     * 查询小组明细
     * @param groupId
     * @param ruleId
     * @param objectId
     * @param createTime
     * @return
     */
    String  selectRuleInfoByGroupId(@Param("groupId") String groupId, @Param("ruleId") String ruleId , @Param("objectId") String objectId ,
                                    @Param("createTime") String createTime);

    /**
     * 查询帖子评论量
     * @param groupId
     * @return
     */
    List<PostsViewInfo> selectPostsCommentNum(String groupId);

    String selectProjectByGroupId(String groupId);

    /**
     *判断用户在小组是否打卡
     * @param userId
     * @param groupId
     * @return
     */
    int selectIsSignByUser(@Param("userId") String userId, @Param("groupId") String groupId);

    /**
     * 判断用户在小组是否发帖
     * @param userId
     * @param groupId
     * @return
     */
    int selectIsPostsByUser(@Param("userId") String userId, @Param("groupId") String groupId);

}
