package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizComment;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帖子(活动)评论表
 * 
 * @author huangxl
 * @Date 2019-01-28 15:06:24
 */
public interface BizCommentMapper extends CommonMapper<BizComment> {

    /**
     * 查询评论列表
     * @param postsId
     * @return
     */
    List<CommentVo> selectCommentList(@Param("postsId") String postsId, @Param("page") Integer page, @Param("limit") Integer limit);


    int selectCommentCount(@Param("postsId") String postsId);

    /**
     * 隐藏与显示评论
     * @param userId
     * @param status
     * @param id
     * @return
     */
    int updateCommentStatusById(@Param("userId") String userId, @Param("status") String status,@Param("id") String id);

}
