package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopic;
import com.github.wxiaoqi.security.jinmao.mapper.BizChamberTopicMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizChamberTopicSelectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizChamberTopicVoteMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizFamilyPostsMapper;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicVo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.SelContentVo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.GradeRuleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
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
    private BizChamberTopicSelectMapper bizChamberTopicSelectMapper;
    @Autowired
    private BizFamilyPostsMapper bizFamilyPostsMapper;
    @Autowired
    private BizChamberTopicVoteMapper bizChamberTopicVoteMapper;

    /**
     * 查询议事厅话题列表
     * @param projectId
     * @param tagId
     * @param topicType
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ChamberTopicVo> getChamberTopicList(String projectId,String tagId,  String topicType, String showType,String isTop,
                                                    String startTime,String endTime, String searchVal,Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<ChamberTopicVo> topicVoList =  bizChamberTopicMapper.selectChamberTopicList(projectId, tagId, topicType, showType, isTop, startTime,
                endTime, searchVal, startIndex, limit);
        if(topicVoList != null && topicVoList.size() >0){
            for (ChamberTopicVo topicVo : topicVoList){
                topicVo.setGradeTitle(getGradeTitle(topicVo.getUserId()));
            }
        }
        return topicVoList;
    }

    /**
     * 查询议事厅话题列表数量
     * @param projectId
     * @param tagId
     * @param topicType
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    public int selectChamberTopicCount(String projectId,String tagId,  String topicType, String showType,String isTop,
                                       String startTime,String endTime, String searchVal){
        return bizChamberTopicMapper.selectChamberTopicCount(projectId, tagId, topicType, showType, isTop, startTime, endTime, searchVal);
    }


    /**
     * 查询议事厅话题详情
     * @param id
     * @return
     */
    public ObjectRestResponse<ChamberTopicInfo> getChamberTopicInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        ChamberTopicInfo chamberTopicInfo = bizChamberTopicMapper.selectChamberTopicInfo(id);
        if(chamberTopicInfo != null){
            //查询发帖人等级头衔
            chamberTopicInfo.setGradeTitle(getGradeTitle(chamberTopicInfo.getUserId()));
            //处理投票选项
            List<SelContentVo> contentVoList = bizChamberTopicSelectMapper.selectSelContentList(id);
            if(contentVoList != null && contentVoList.size() >0 ){
                for (SelContentVo temp : contentVoList){
                    //选项投票人数
                   int selNum =  bizChamberTopicVoteMapper.selectSelNumById(temp.getId(),id);
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

    public String getGradeTitle(String userId){
        //查询发帖人等级头衔
        int uValue = 0;
        int uPoint = 0;
       // String userValue = bizFamilyPostsMapper.selectUserValueById(userId);
        String userPoint = bizFamilyPostsMapper.selectUserPointById(userId);
       /* if(userValue != null || !StringUtils.isEmpty(userValue)){
            uValue = Integer.parseInt(userValue);
        }*/
        if(userPoint != null || !StringUtils.isEmpty(userPoint)){
            uPoint = Integer.parseInt(userPoint);
        }
        int total = uValue + uPoint;
        String gradeTitle = "";
        List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
        if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
            for (GradeRuleVo vo : gradeRuleVoList){
                if(total >= vo.getIntegral()){
                    gradeTitle = vo.getGradeTitle();
                }
            }
        }
        return gradeTitle;
    }



    /**
     * 议事厅话题操作置顶,隐藏
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