package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.jinmao.entity.BizFamilyPosts;
import com.github.wxiaoqi.security.jinmao.mapper.BaseAppClientUserMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizFamilyPostsMapper;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.GradeRuleVo;
import com.github.wxiaoqi.security.jinmao.vo.familyPosts.FamilyPostsInfo;
import com.github.wxiaoqi.security.jinmao.vo.familyPosts.FamilyPostsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 家里人帖子表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Service
public class BizFamilyPostsBiz extends BusinessBiz<BizFamilyPostsMapper,BizFamilyPosts> {

    @Autowired
    private BizFamilyPostsMapper bizFamilyPostsMapper;

    @Autowired
    BaseAppClientUserMapper baseAppClientUserMapper;


    /**
     * 查询家里人帖子列表
     * @param projectId
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<FamilyPostsVo> getFamilyPostsList(String projectId, String showType, String isTop, String startTime, String endTime,
                                                  String searchVal, Integer page, Integer limit){
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
//        List<FamilyPostsVo> postsVoList =  bizFamilyPostsMapper.selectFamilyPostsList(projectId, showType, isTop, startTime, endTime, searchVal, startIndex, limit);
        List<FamilyPostsVo> postsVoList =  bizFamilyPostsMapper.selectNewFamilyPostsList(projectId, showType, isTop, startTime, endTime, searchVal, startIndex, limit);
        if(postsVoList == null && postsVoList.size() == 0){
            postsVoList = new ArrayList<>();
        }else{
            for (FamilyPostsVo postsVo: postsVoList){
                //查询发帖人等级头衔
                postsVo.setGradeTitle(getGradeTitle(postsVo.getUserId()));
                BaseAppClientUser baseAppClientUser = baseAppClientUserMapper.selectByPrimaryKey(postsVo.getUserId());
                if (baseAppClientUser != null) {
                    postsVo.setUserName(baseAppClientUser.getNickname());
                }
            }
        }
        return postsVoList;
    }


    /**
     * 查询家里人帖子列表数量
     * @param projectId
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    public int selectFamilyPostsCount (String projectId, String showType, String isTop, String startTime, String endTime,
                                       String searchVal){
        return bizFamilyPostsMapper.selectFamilyPostsCount(projectId, showType, isTop, startTime, endTime, searchVal);
    }


    /**
     * 查询家里人帖子详情
     * @param id
     * @return
     */
    public ObjectRestResponse<FamilyPostsInfo> getFamilyPostsInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        FamilyPostsInfo familyPostsInfo = bizFamilyPostsMapper.selectFamilyPostsInfo(id);
        if(familyPostsInfo != null){
            //查询发帖人等级头衔
            familyPostsInfo.setGradeTitle(getGradeTitle(familyPostsInfo.getUserId()));
        }
        return ObjectRestResponse.ok(familyPostsInfo);
    }


    public String getGradeTitle(String userId){
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
     *  家里人帖子操作置顶,隐藏
     * @param param
     * @return
     */
    public ObjectRestResponse updateFamilyPostsStatus(UpdateTopicStatus param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(501);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(bizFamilyPostsMapper.updateFamilyPostsStatusById(param.getStatus(),param.getId(), BaseContextHandler.getUserID()) < 0){
            msg.setStatus(503);
            msg.setMessage("操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }







}