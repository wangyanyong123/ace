package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizContentReader;
import com.github.wxiaoqi.security.app.vo.goodvisit.out.GoodVisitReaderVo;
import com.github.wxiaoqi.security.app.vo.posts.out.ReaderInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 内容(产品)阅读数表
 * 
 * @author zxl
 * @Date 2018-12-13 09:56:49
 */
public interface BizContentReaderMapper extends CommonMapper<BizContentReader> {

    /**
     * 查找阅读表的阅读量和用户量
     * @param id
     * @return
     */
    GoodVisitReaderVo selectContentReader(@Param("id") String id);

    /**
     * 查询阅读信息
     * @param postsId
     * @return
     */
    ReaderInfo selectReaderInfoByPostsId(String postsId);
	
}
