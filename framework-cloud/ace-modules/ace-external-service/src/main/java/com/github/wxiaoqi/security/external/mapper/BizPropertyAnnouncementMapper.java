package com.github.wxiaoqi.security.external.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.external.entity.BizPropertyAnnouncement;
import com.github.wxiaoqi.security.external.vo.AnnInfoVo;
import com.github.wxiaoqi.security.external.vo.ResultAnnList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业公告
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-26 13:57:07
 */
public interface BizPropertyAnnouncementMapper extends CommonMapper<BizPropertyAnnouncement> {

    /**
     * 查询物业公告列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<ResultAnnList> selectAnnouncementList(@Param("projectId") String projectId, @Param("page") Integer page, @Param("limit") Integer limit);






    /**
     * 查询物业公告详情
     * @param id
     * @return
     */
    AnnInfoVo selectAnnouncementInfo(String id);


	
}
