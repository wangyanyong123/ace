package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizOperatingDetail;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 评论点赞详情表
 * 
 * @author zxl
 * @Date 2018-12-18 10:45:45
 */
public interface BizOperatingDetailMapper extends CommonMapper<BizOperatingDetail> {

    /**
     * 取消点赞则删除点赞记录
     * @param userId
     * @param commentId
     * @return
     */
    int delLikeDetail(@Param("userId") String userId, @Param("commentId") String commentId);

    /**
     * 判断当前用户是否点赞
     * @param userId
     * @param objectId
     * @return
     */
    String selectIsUpByUserId(@Param("userId") String userId, @Param("objectId") String objectId);



}
