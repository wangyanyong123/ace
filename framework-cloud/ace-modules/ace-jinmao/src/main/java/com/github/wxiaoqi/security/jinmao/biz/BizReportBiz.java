package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.entity.BizReport;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizReportMapper;
import com.github.wxiaoqi.security.jinmao.vo.report.in.StatusParam;
import com.github.wxiaoqi.security.jinmao.vo.report.out.FeedbackVo;
import com.github.wxiaoqi.security.jinmao.vo.report.out.ReportPersonVo;
import com.github.wxiaoqi.security.jinmao.vo.report.out.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 举报管理表
 *
 * @author huangxl
 * @Date 2019-03-04 17:13:39
 */
@Service
public class BizReportBiz extends BusinessBiz<BizReportMapper,BizReport> {

    @Autowired
    private BizReportMapper bizReportMapper;
    @Autowired
    private BizProductMapper bizProductMapper;

    /**
     * 查询举报列表
     * @param projectId
     * @param groupId
     * @param type
     * @param searchval
     * @param page
     * @param limit
     * @return
     */
    public List<ReportVo> getReportList(String projectId, String groupId, String type, String searchval,
                                        Integer page, Integer limit){
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
        //查询当前租户id的身份
        String tenantType = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(tenantType)){
            projectId = bizProductMapper.selectProjectById(BaseContextHandler.getTenantID());
        }
        List<ReportVo> reportVoList = bizReportMapper.selectReportList(projectId, groupId, type, searchval, startIndex, limit);
        if(reportVoList == null || reportVoList.size() == 0){
            reportVoList = new ArrayList<>();
        }
        return reportVoList;
    }

    /**
     * 查询举报列表数量
     * @param projectId
     * @param groupId
     * @param type
     * @param searchval
     * @return
     */
    public int selectReportCount(String projectId, String groupId, String type, String searchval){
        return bizReportMapper.selectReportCount(projectId, groupId, type, searchval);
    }

    /**
     * 允许/禁止评论,发帖
     * @param param
     * @return
     */
    public ObjectRestResponse updateForbidStatus(StatusParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
       if(bizReportMapper.updateForbidStatus(param.getStatus(), BaseContextHandler.getUserID(),param.getBeUserId()) < 0){
           msg.setStatus(201);
           msg.setMessage("操作失败!");
       }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查看举报人信息
     * @param reportId
     * @param searchval
     * @param page
     * @param limit
     * @return
     */
    public List<ReportPersonVo> getReportPersonList(String reportId,String searchval, Integer page, Integer limit){
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
        List<ReportPersonVo> reportPersonVoList = bizReportMapper.seelctReportPersonList(reportId, searchval, startIndex, limit);
        if(reportPersonVoList == null || reportPersonVoList.size() == 0){
            reportPersonVoList = new ArrayList<>();
        }
        return reportPersonVoList;
    }


    public int seelctReportPersonCount(String reportId,String searchval){
        return bizReportMapper.seelctReportPersonCount(reportId, searchval);
    }

    /**
     * 根据用户id查询反馈信息
     * @param userId
     * @return
     */
    public List<FeedbackVo> getFeedbackList(String userId,String searchval, Integer page, Integer limit){
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
        List<FeedbackVo> feedbackVoList = bizReportMapper.selectFeedbackByUserId(userId,searchval,startIndex,limit);
        if(feedbackVoList == null || feedbackVoList.size() == 0){
            feedbackVoList = new ArrayList<>();
        }
        return feedbackVoList;
    }

}