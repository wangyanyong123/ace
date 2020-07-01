package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizReport;
import com.github.wxiaoqi.security.app.entity.BizReportFeedback;
import com.github.wxiaoqi.security.app.entity.BizReportPerson;
import com.github.wxiaoqi.security.app.fegin.SmsFeign;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.activity.out.UserInfo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.report.FeedbackParam;
import com.github.wxiaoqi.security.app.vo.report.ReportParam;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private BizReportFeedbackMapper bizReportFeedbackMapper;
    @Autowired
    private BizReportPersonMapper bizReportPersonMapper;
    @Autowired
    private BizActivityApplyMapper bizActivityApplyMapper;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private SmsFeign smsFeign;

    private Logger logger = LoggerFactory.getLogger(BizReportBiz.class);

    /**
     * 举报
     * @param param
     * @return
     */
    public ObjectRestResponse saveReport(ReportParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        String isReport = bizReportMapper.selectIsReportByUser(BaseContextHandler.getUserID(),param.getType(),
                param.getCommentId(),param.getPostsId());
        if(isReport != null && !"".equals(isReport)){
            msg.setStatus(501);
            msg.setMessage("你已举报了!");
            return msg;
        }
        //判断用户当前角色
        CurrentUserInfosVo userInfosvo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
        if("4".equals(userInfosvo.getIdentityType())){
            msg.setStatus(201);
            msg.setMessage("请前去选择身份验证!");
            return msg;
        }
        String msgTheme = "";
        //维护举报管理表
        BizReport reportInfo = bizReportMapper.selectReportInfo(param.getType(),param.getCommentId(),param.getPostsId());
        String reportId = UUIDUtils.generateUuid();
        String beReportPerson = "";
        //获取Apollo配置
        Config config = ConfigService.getConfig("ace-app");
        String property = config.getProperty("report.reportCount", "");
        String propertyTel = config.getProperty("report.reportTel", "");
        if(reportInfo == null){
            //新增
            com.github.wxiaoqi.security.app.vo.report.out.UserInfo userInfo = null;
            if("1".equals(param.getType())){
                userInfo = bizReportMapper.selectUserByPosts(param.getPostsId());
            }else{
                userInfo = bizReportMapper.selectUserByComment(param.getCommentId(),param.getPostsId());
            }
            BizReport bizReport = new BizReport();
            bizReport.setId(reportId);
            bizReport.setPostsId(param.getPostsId());
            bizReport.setCommentId(param.getCommentId());
            if(userInfo != null){
                beReportPerson = userInfo.getName();
                bizReport.setBeUserId(userInfo.getUserId());
                bizReport.setBeReportPerson(beReportPerson);
                bizReport.setBeReportTel(userInfo.getTel());
            }
            bizReport.setReportCount("1");
            bizReport.setStatus("1");
            bizReport.setType(param.getType());
            bizReport.setCreateBy(BaseContextHandler.getUserID());
            bizReport.setCreateTime(new Date());
            bizReport.setTimeStamp(new Date());
            if(bizReportMapper.insertSelective(bizReport) < 0){
                msg.setStatus(201);
                msg.setMessage("举报失败!");
                return msg;
            }
            msgTheme = MsgThemeConstants.ADD_REPORT;
        }else{
            //更新次数和显示状态
            reportId = reportInfo.getId();
            beReportPerson = reportInfo.getBeReportPerson();
            BizReport report = new BizReport();
            report.setId(reportId);
            int reportCount = Integer.valueOf(reportInfo.getReportCount())+1;
            //评论超过3人或以上举报，系统自动隐藏此评论和帖子
            if(reportCount> Integer.parseInt(property)) {
                if("1".equals(param.getType())){
                    bizReportMapper.hidePosts(BaseContextHandler.getUserID(),param.getPostsId());
                }else{
                    bizReportMapper.hideComment(BaseContextHandler.getUserID(),param.getCommentId());
                }
            }
            report.setReportCount(reportCount+"");
            report.setTimeStamp(new Date());
            report.setModifyTime(new Date());
            report.setModifyBy(BaseContextHandler.getUserID());
            if(bizReportMapper.updateByPrimaryKeySelective(report) < 0){
                msg.setStatus(201);
                msg.setMessage("举报失败!");
                return msg;
            }
            msgTheme = MsgThemeConstants.ADD_REPORT;
        }
       //维护举报人表
        String userId = BaseContextHandler.getUserID();
        UserInfo userInfo = bizActivityApplyMapper.selectUserInfo(userId);
        BizReportPerson person = new BizReportPerson();
        person.setId(UUIDUtils.generateUuid());
        person.setReportId(reportId);
        person.setReportPersonId(userId);
        if(userInfo != null){
            person.setReportPerson(userInfo.getName());
            person.setContact(userInfo.getTel());
        }
        person.setReportReason(param.getReportReason());
        person.setCreateBy(BaseContextHandler.getUserID());
        person.setCreateTime(new Date());
        person.setTimeStamp(new Date());
        bizReportPersonMapper.insertSelective(person);

        //发送短信
        if(!com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(msgTheme)){
            Map<String,String> paramMap = new HashMap<>();
            //paramMap.put("projectName",tenantProjectMapper.selectProjectNameByTenantId(BaseContextHandler.getTenantID()));
            paramMap.put("userName", beReportPerson);
            System.out.println(JSON.toJSONString(paramMap));
            ObjectRestResponse result = smsFeign.sendMsg(propertyTel,0,0,"2",msgTheme,JSON.toJSONString(paramMap));
            if(result.getStatus() != 200){
                logger.error("发送短信通知失败！");
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 意见反馈
     * @param param
     * @return
     */
    public ObjectRestResponse saveReportFeedback(FeedbackParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(param.getContent())){
            msg.setStatus(201);
            msg.setMessage("反馈内容不能为空!");
            return msg;
        }
        String userId = BaseContextHandler.getUserID();
        UserInfo userInfo = bizActivityApplyMapper.selectUserInfo(userId);
        BizReportFeedback feedback = new BizReportFeedback();
        feedback.setId(UUIDUtils.generateUuid());
        feedback.setUserId(userId);
        if(userInfo != null){
            feedback.setUserName(userInfo.getName());
            feedback.setContact(userInfo.getTel());
        }
        feedback.setContent(param.getContent());
        feedback.setSource(param.getSource());
        feedback.setCreateBy(userId);
        feedback.setCreateTime(new Date());
        feedback.setTimeStamp(new Date());
        if(bizReportFeedbackMapper.insertSelective(feedback) < 0){
            msg.setStatus(201);
            msg.setMessage("提交反馈失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

}