package com.github.wxiaoqi.security.external.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.external.entity.BizPropertyAnnouncement;
import com.github.wxiaoqi.security.external.mapper.BizPropertyAnnouncementMapper;
import com.github.wxiaoqi.security.external.vo.AnnInfoVo;
import com.github.wxiaoqi.security.external.vo.ResultAnnList;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 物业公告
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-26 13:57:07
 */
@Service
public class BizPropertyAnnouncementBiz extends BusinessBiz<BizPropertyAnnouncementMapper, BizPropertyAnnouncement> {

    @Autowired
    private BizPropertyAnnouncementMapper bizPropertyAnnouncementMapper;

    /**
     * 查询物业公告列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse getAnnouncementList(String projectId, Integer page, Integer limit){
        ObjectRestResponse response = new ObjectRestResponse();
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<ResultAnnList> announcementVoList = bizPropertyAnnouncementMapper.selectAnnouncementList(projectId, startIndex, limit);
        response.setData(announcementVoList);
        response.setStatus(200);
        return response;
    }

    /**
     * 查询物业公告详情
     * @param id
     * @return
     */
    public ObjectRestResponse getAnnouncementInfo(String id){
        ObjectRestResponse response = new ObjectRestResponse();
        AnnInfoVo announcementInfo = bizPropertyAnnouncementMapper.selectAnnouncementInfo(id);
        if(announcementInfo == null){
            announcementInfo  = new AnnInfoVo();
        }
        response.setData(announcementInfo);
        response.setStatus(200);
        return response;
    }



}