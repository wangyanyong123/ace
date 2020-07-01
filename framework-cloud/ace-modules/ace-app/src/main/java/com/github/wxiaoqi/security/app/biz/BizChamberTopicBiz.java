package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizChamberTopic;
import com.github.wxiaoqi.security.app.entity.BizChamberTopicVote;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.topic.in.SaveVoteParam;
import com.github.wxiaoqi.security.app.vo.topic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.app.vo.topic.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 议事厅话题表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Service
public class BizChamberTopicBiz extends BusinessBiz<BizChamberTopicMapper,BizChamberTopic> {

    @Autowired
    private BizChamberTopicMapper bizChamberTopicMapper;
    @Autowired
    private BizFamilyPostsMapper bizFamilyPostsMapper;
    @Autowired
    private BizChamberTopicSelectMapper bizChamberTopicSelectMapper;
    @Autowired
    private BizChamberTopicVoteMapper bizChamberTopicVoteMapper;
    @Autowired
    private BizCommunityTopicBiz bizCommunityTopicBiz;
    @Autowired
    private BizCommunityTopicMapper bizCommunityTopicMapper;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;


    /**
     * 查询议事厅话题列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<ChamberTopicVo>> getChamberTopicList(String type, String projectId, Integer page, Integer limit){
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
        String all = "1";
        String my = "2";
        String userId = BaseContextHandler.getUserID();
        List<ChamberTopicVo> chamberTopicVoList = new ArrayList<>();
        if(all.equals(type)){
            chamberTopicVoList = bizChamberTopicMapper.selectChamberTopicList(projectId, userId,null,startIndex,limit);
        }else if(my.equals(type)){
            chamberTopicVoList = bizChamberTopicMapper.selectChamberTopicList(projectId, userId,userId,startIndex,limit);
        }
        if(chamberTopicVoList != null && chamberTopicVoList.size() >0){
            for (ChamberTopicVo topicVo: chamberTopicVoList){
                String gradeTitle = "";
                List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
                if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
                    for (GradeRuleVo vo : gradeRuleVoList){
                        if(getGradeValue(topicVo.getUserId()) >= vo.getIntegral()){
                            gradeTitle = vo.getGradeTitle();
                        }
                    }
                    topicVo.setGradeTitle(gradeTitle);
                }
            }
        }
        return ObjectRestResponse.ok(chamberTopicVoList);
    }


    /**
     * 查询用户参与的议事厅话题列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<ChamberTopicVo>> getMyplayerChamberTopicList(String projectId, Integer page, Integer limit){
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
        String userId = BaseContextHandler.getUserID();
        List<ChamberTopicVo> chamberTopicVoList = bizChamberTopicMapper.selectMyplayerTopicList(projectId,userId,startIndex,limit);
        if(chamberTopicVoList != null && chamberTopicVoList.size() >0){
            for (ChamberTopicVo topicVo: chamberTopicVoList){
                String gradeTitle = "";
                List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
                if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
                    for (GradeRuleVo vo : gradeRuleVoList){
                        if(getGradeValue(topicVo.getUserId()) >= vo.getIntegral()){
                            gradeTitle = vo.getGradeTitle();
                        }
                    }
                    topicVo.setGradeTitle(gradeTitle);
                }
            }
        }
        return ObjectRestResponse.ok(chamberTopicVoList);
    }

    public int getGradeValue(String userId){
        //查询发帖人等级头衔
        int uValue = 0;
        int uPoint = 0;
        //String userValue = bizFamilyPostsMapper.selectUserValueById(userId);
        String userPoint = bizFamilyPostsMapper.selectUserPointById(userId);
       /* if(userValue != null || !StringUtils.isEmpty(userValue)){
            uValue = Integer.parseInt(userValue);
        }*/
        if(userPoint != null || !StringUtils.isEmpty(userPoint)){
            uPoint = Integer.parseInt(userPoint);
        }
        int total = uValue + uPoint;

        return total;
    }


    /**
     * 查询议事厅话题标签列表
     * @return
     */
    public ObjectRestResponse<List<TagVo>> getTagList(String projectId){
        List<TagVo> tagVoList = bizChamberTopicMapper.selectTagList(projectId);
        return ObjectRestResponse.ok(tagVoList);
    }


    /**
     *查询议事厅话题详情
     * @param id
     * @param projectId
     * @return
     */
    public ObjectRestResponse<ChamberTopicInfo> getChamberTopicInfo(final String id,final String projectId,String tempvo){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        //统计阅读数
        String str = "0";
        final String userId = BaseContextHandler.getUserID();
        if(str.equals(tempvo)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bizCommunityTopicBiz.addReaderDetail(id,projectId,"2",userId);
                }
            }).start();
        }
        ChamberTopicInfo chamberTopicInfo = bizChamberTopicMapper.selectChamberTopicInfo(id, BaseContextHandler.getUserID());
        if(chamberTopicInfo != null){
            String isShow = "0";
            if(isShow.equals(chamberTopicInfo.getShowType())){
                msg.setStatus(103);
                msg.setMessage("该帖已被隐藏,不支持查看详情!");
                return msg;
            }
            //判断用户当前角色
            CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
            if(userInfo != null){
                if("4".equals(userInfo.getIdentityType())){
                    chamberTopicInfo.setIdentityType("1");
                }else{
                    chamberTopicInfo.setIdentityType("0");
                }
            }
            if(bizCommunityTopicMapper.selectIsOperationByUserId(BaseContextHandler.getUserID()) > 0){
                chamberTopicInfo.setIdentityType("2");
            }
             String bo = "2";
             //投票信息
             if(bo.equals(chamberTopicInfo.getTopicType())){
                 String def = "1";
                 if(def.equals(chamberTopicInfo.getSelection())){
                     chamberTopicInfo.setSelectionStr("单选");
                 }else{
                     chamberTopicInfo.setSelectionStr("最多选"+chamberTopicInfo.getSelection()+"项");
                 }
             }
             //获取投票列表
            List<SelContentVo> contentVoList = bizChamberTopicSelectMapper.selectBallotSelectList(id,BaseContextHandler.getUserID());
            if(contentVoList != null && contentVoList.size() >0 ){
                for (SelContentVo temp : contentVoList){
                    //选项投票人数
                    int selNum =  bizChamberTopicSelectMapper.selectSelNumById(temp.getId(),id);
                    if(selNum == 0){
                        temp.setPercent("0%");
                    }else{
                        // 创建一个数值格式化对象
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        // 设置精确到小数点后2位
                        numberFormat.setMaximumFractionDigits(0);
                        String result = numberFormat.format((float)selNum/(float)chamberTopicInfo.getPlayerNum()*100);
                        temp.setPercent(result + "%");
                    }
                }
                chamberTopicInfo.setContentVo(contentVoList);
            }
        }
        return ObjectRestResponse.ok(chamberTopicInfo);
    }


    /**
     * 用户投票
     * @param param
     * @return
     */
    public ObjectRestResponse saveTopicVote(SaveVoteParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(202);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(param.getSelectId() == null || param.getSelectId().length == 0){
            msg.setStatus(203);
            msg.setMessage("投票选项不能为空!");
            return msg;
        }
        ChamberTopicInfo chamberTopicInfo = bizChamberTopicMapper.selectChamberTopicInfo(param.getTopicId(), BaseContextHandler.getUserID());
        if(chamberTopicInfo != null) {
            String isShow = "0";
            if (isShow.equals(chamberTopicInfo.getShowType())) {
                msg.setStatus(103);
                msg.setMessage("该帖已被隐藏,不支持投票!");
                return msg;
            }
            if(compareDate(chamberTopicInfo.getEndTime()) < 0){
                msg.setStatus(103);
                msg.setMessage("该帖子投票时间已截止!");
                return msg;
            }
        }
        //判断用户当前角色
        CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
        if(userInfo != null){
            if(bizCommunityTopicMapper.selectIsOperationByUserId(BaseContextHandler.getUserID()) <= 0){
                if("4".equals(userInfo.getIdentityType())){
                    msg.setStatus(201);
                    msg.setMessage("请前去选择身份验证!");
                    return msg;
                }
            }
        }
        BizChamberTopicVote vote = new BizChamberTopicVote();
        String[] selectVo = param.getSelectId();
        for (int i = 0; i < selectVo.length; i++) {
            vote.setId(UUIDUtils.generateUuid());
            vote.setTopicId(param.getTopicId());
            vote.setSelectId(selectVo[i]);
            vote.setUserId(BaseContextHandler.getUserID());
            vote.setCreateBy(BaseContextHandler.getUserID());
            vote.setCreateTime(new Date());
            vote.setTimeStamp(new Date());
            if(bizChamberTopicVoteMapper.insertSelective(vote)< 0){
                msg.setStatus(203);
                msg.setMessage("投票失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    public int compareDate(String DATE1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = new Date();
            if (dt1.getTime() >= dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }



    /**
     *  议事厅话题操作置顶,隐藏
     * @param param
     * @return
     */
    public ObjectRestResponse updateChamberTopicStatus(UpdateTopicStatus param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(501);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        ChamberTopicInfo chamberTopicInfo = bizChamberTopicMapper.selectChamberTopicInfo(param.getId(), BaseContextHandler.getUserID());
        if(chamberTopicInfo != null) {
            String isShow = "0";
            if (isShow.equals(chamberTopicInfo.getShowType())) {
                msg.setStatus(103);
                msg.setMessage("该帖已被隐藏,不支持任何操作!");
                return msg;
            }
        }
        //判断用户当前角色
        if(bizCommunityTopicMapper.selectIsOperationByUserId(BaseContextHandler.getUserID()) <= 0){
            msg.setStatus(501);
            msg.setMessage("请前去注册运营人员账号!");
            return msg;
        }
        if(bizChamberTopicMapper.updateChamberTopicStatusById(param.getStatus(),param.getId(), BaseContextHandler.getUserID()) < 0){
            msg.setStatus(503);
            msg.setMessage("操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }





















}