package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizContentReaderDetail;
import com.github.wxiaoqi.security.app.vo.goodvisit.out.ContentAndUser;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 内容(产品)阅读详情表
 * 
 * @author zxl
 * @Date 2018-12-13 09:56:49
 */
public interface BizContentReaderDetailMapper extends CommonMapper<BizContentReaderDetail> {

    /**
     * 查询是否阅读细节
     * @return
     */
    ContentAndUser selectContentDetail(@Param("userId") String userId,@Param("contentId")String contentId);

    int updateReaderTime(@Param("contentId") String contentId, @Param("userId") String userId);

    /**
     * 查询阅读的用户
     * @param postsId
     * @return
     */
    List<String> selectUserByPostsId(String postsId);

}
