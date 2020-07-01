package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizComment;
import com.github.wxiaoqi.security.app.vo.posts.out.CommentVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帖子(活动)评论表
 * 
 * @author zxl
 * @Date 2018-12-18 10:45:45
 */
public interface BizCommentMapper extends CommonMapper<BizComment> {

    /**
     * 查询评论列表
     * @param postsId
     * @return
     */
    List<CommentVo> selectCommentList(@Param("userId") String userId, @Param("postsId") String postsId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 分享评论列表
     * @param postsId
     * @return
     */
    List<CommentVo> shareCommentList(@Param("postsId") String postsId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询评论的数量
     * @param postsId
     * @return
     */
    int selectCommentCount(String postsId);

    /**
     * 查询评论点赞数
     * @param id
     * @return
     */
    int selectUpNumById(String id);

    /**
     * 隐藏父级评论
     * @param id
     * @param userId
     * @return
     */
    int updateCommentTypeById(@Param("id") String id, @Param("userId") String userId);

    /**
     * 隐藏父级评论下的子级评论
     * @param pid
     * @param userId
     * @return
     */
    int updatePcommentTypeByPId(@Param("pid") String pid, @Param("userId") String userId);

    /**
     * 根据评论id查询所属小组id
     * @param commentId
     * @return
     */
    String selectGroupIdById(String commentId);

    /**
     * 根据评论id查询对象id
     * @param commentId
     * @return
     */
    String selectObjectIdBycommentId(String commentId);
}
