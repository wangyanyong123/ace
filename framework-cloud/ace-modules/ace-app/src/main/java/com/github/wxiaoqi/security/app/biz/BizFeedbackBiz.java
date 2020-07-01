package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizFeedback;
import com.github.wxiaoqi.security.app.mapper.BaseAppClientUserMapper;
import com.github.wxiaoqi.security.app.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.app.mapper.BizFeedbackMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.feedback.SaveFeedback;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 意见反馈
 *
 * @author zxl
 * @Date 2019-01-08 11:00:06
 */
@Service
public class BizFeedbackBiz extends BusinessBiz<BizFeedbackMapper,BizFeedback> {

    @Autowired
    private BizFeedbackMapper bizFeedbackMapper;
    @Autowired
    private BaseAppClientUserMapper baseAppClientUserMapper;
    @Autowired
    private BaseAppServerUserMapper baseAppServerUserMapper;


    /**
     * 提交反馈
     * @param param
     * @return
     */
    public ObjectRestResponse saveFeedback(SaveFeedback param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(param.getContent())){
            msg.setStatus(201);
            msg.setMessage("反馈内容不能为空!");
        }
        String userId = BaseContextHandler.getUserID();
        UserVo userInfo = new UserVo();
        if("2".equals(param.getSource()) || "4".equals(param.getSource())){
            userInfo = baseAppServerUserMapper.getUserNameById(userId);
        }else{
            userInfo = baseAppClientUserMapper.getUserNameById(userId);
        }
        BizFeedback feedback = new BizFeedback();
        feedback.setId(UUIDUtils.generateUuid());
        feedback.setUserId(userId);
        if(userInfo != null){
            feedback.setUserName(userInfo.getName());
            feedback.setUserTel(userInfo.getMobilePhone());
        }
        feedback.setProjectId(param.getProjectId());
        feedback.setHourseId(param.getHourseId());
        feedback.setContent(param.getContent());
        feedback.setSource(param.getSource());
        feedback.setTimeStamp(new Date());
        feedback.setCreateBy(userId);
        feedback.setCreateTime(new Date());
        if(bizFeedbackMapper.insertSelective(feedback) < 0){
            msg.setStatus(202);
            msg.setMessage("提交反馈失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }






}