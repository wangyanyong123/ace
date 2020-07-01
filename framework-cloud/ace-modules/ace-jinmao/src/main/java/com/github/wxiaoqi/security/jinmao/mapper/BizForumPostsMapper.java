package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizForumPosts;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.FostsVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.GroupInfo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.PostsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组帖子表
 * 
 * @author huangxl
 * @Date 2019-01-28 15:06:24
 */
public interface BizForumPostsMapper extends CommonMapper<BizForumPosts> {

    /**
     * 查询帖子列表
     * @param projectId
     * @param groupId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<FostsVo> selectFostsList(@Param("projectId") String projectId,  @Param("isTop") String isTop,@Param("groupId") String groupId,
                                  @Param("startTime") String startTime, @Param("endTime") String endTime,
                                  @Param("searchVal") String searchVal,
                                  @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询帖子列表数量
     * @param projectId
     * @param groupId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    int selectPostsCount(@Param("projectId") String projectId, @Param("isTop") String isTop, @Param("groupId") String groupId,
                         @Param("startTime") String startTime, @Param("endTime") String endTime,
                         @Param("searchVal") String searchVal);


    /**
     * 帖子操作置顶,隐藏,精华
     * @param status (0-隐藏,1-显示,2-精华,3-置顶,4-取消置顶,5-取消精华)
     * @param id
     * @param userId
     * @return
     */
    int updatePostsStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);

    /**
     * 查询帖子详情
     * @param id
     * @return
     */
    PostsInfo selectPostsInfo(String id);

    /**
     * 根据项目id查询所关联的小组
     * @param projectId
     * @return
     */
    List<GroupInfo> selectGroupByProjectId(@Param("projectId") String projectId);
	
}
