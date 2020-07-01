package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReport;
import com.github.wxiaoqi.security.app.vo.report.out.UserInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 举报管理表
 * 
 * @author huangxl
 * @Date 2019-03-04 17:13:39
 */
public interface BizReportMapper extends CommonMapper<BizReport> {


    /**
     * 查询评论被举报人信息
     * @param commentId
     * @param objectId
     * @return
     */
    UserInfo selectUserByComment(@Param("commentId") String commentId, @Param("objectId") String objectId);

    /**
     * 查询帖子被举报人信息
     * @param postsId
     * @return
     */
    UserInfo selectUserByPosts(String postsId);

    /**
     * 查询举报信息
     * @param type
     * @param commentId
     * @param postsId
     * @return
     */
    BizReport selectReportInfo(@Param("type") String type,@Param("commentId") String commentId, @Param("postsId") String postsId);


    int hideComment(@Param("userId") String userId, @Param("id") String id);

    int hidePosts(@Param("userId") String userId, @Param("id") String id);

    /**
     * 判断用户是否有举报行为
     * @param userId
     * @param type
     * @param commentId
     * @param postsId
     * @return
     */
    String selectIsReportByUser(@Param("userId") String userId,@Param("type") String type,
                                @Param("commentId") String commentId, @Param("postsId") String postsId);
	
}
