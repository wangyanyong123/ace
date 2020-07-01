package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizPropertyAnnouncement;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAnnouncementInfo;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAnnouncementVo;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAppAnnouncementInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAppAnnouncementVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业公告
 *
 * @author Mr.AG
 * @version 2018-11-26 13:57:07
 * @email 463540703@qq.com
 */
public interface BizPropertyAnnouncementMapper extends CommonMapper<BizPropertyAnnouncement> {

    /**
     * 查询物业公告列表
     *
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultAnnouncementVo> selectAnnouncementList(@Param("id") String id, @Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                                      @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 根据条件查询物业公告数量
     *
     * @param tenantId
     * @param searchVal
     * @return
     */
    int selectAnnouncementCount(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal);


    /**
     * 根据公告id删除
     *
     * @param id
     * @param userId
     * @return
     */
    int delAnnouncementInfo(@Param("id") String id, @Param("userId") String userId);

    /**
     * 发布物业公告
     *
     * @param id
     * @param userId
     * @return
     */
    int publisherAnnouncement(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询物业公告详情
     *
     * @param id
     * @return
     */
    ResultAnnouncementInfo selectAnnouncementInfo(String id);


    /**
     * 查询物业公告列表--App
     *
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<ResultAppAnnouncementVo> selectAppAnnouncementList(@Param("projectId") String projectId,
                                                            @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 根据公告id，查询公告详情
     *
     * @param id
     * @return
     */
    ResultAppAnnouncementInfoVo selectAppAnnouncementInfo(String id);

    /**
     * 根据公告id查询公告阅读数量
     *
     * @param id
     * @return
     */
    int selectAppAnnCount(String id);

}
