package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.entity.BizForumPosts;
import com.github.wxiaoqi.security.app.vo.posts.out.PostsInfo;
import com.github.wxiaoqi.security.app.vo.posts.out.PostsVo;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组帖子表
 * 
 * @author zxl
 * @Date 2018-12-18 10:45:45
 */
public interface BizForumPostsMapper extends CommonMapper<BizForumPosts> {

    /**
     * 查询帖子列表
     * @param groupId
     * @return
     */
    List<PostsVo> selectPostsList(@Param("userId") String userId,@Param("groupId") String groupId, @Param("postsType") String postsType,
                                  @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询阅读人的头像
     * @param postsId
     * @return
     */
    List<ImgInfo> selectReaderPhoto(String postsId);

    /**
     * 查询置顶帖子列表
     * @param groupId
     * @param page
     * @param limit
     * @return
     */
    List<PostsVo> selectTopPostsList(@Param("groupId") String groupId, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询帖子详情
     * @param id
     * @return
     */
    PostsInfo selectPostsInfo(@Param("id") String id, @Param("userId") String userId);

    /**
     * 根据帖子id查询所属小组id
     * @param id
     * @return
     */
    String selectGroupIdById(String id);

    /**
     * 帖子操作置顶,隐藏,精华
     * @param status(1-隐藏,2-精华,3-置顶)
     * @param id
     * @param userId
     * @return
     */
    int updatePostsStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);

    /**
     * 查询帖子点赞数
     * @param postsId
     * @return
     */
    int selectUpNumByPostsId(String postsId);

    /**
     * 查询我的帖子
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<FamilyPostsVo> selectPostsListByUser(@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);
    List<FamilyPostsVo> selectNewPostsListByUser(@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 判断当前用户是否被禁止评论/发帖
     * @param userId
     * @return
     */
    int selectIsForbidByUser(String userId);

    /**
     * 删除业主圈帖子
     * @param id
     * @param userId
     * @return
     */
    int delPostsById(@Param("id") String id, @Param("userId") String userId);

    /**
     * 删除议事厅帖子
     * @param id
     * @param userId
     * @return
     */
    int delChamberPostsById(@Param("id") String id, @Param("userId") String userId);

    /**
     * 删除家里帖子
     * @param id
     * @param userId
     * @return
     */
    int delFamilyPostsById(@Param("id") String id, @Param("userId") String userId);
}
