package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizOperatingCount;
import com.github.wxiaoqi.security.app.vo.posts.out.LikeInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

/**
 * 评论点赞表统计
 * 
 * @author zxl
 * @Date 2018-12-18 10:45:45
 */
public interface BizOperatingCountMapper extends CommonMapper<BizOperatingCount> {

    /**
     * 查询评论点赞数
     * @param commentId
     * @return
     */
    LikeInfo selectLikeNumByPostsId(String commentId);
}
