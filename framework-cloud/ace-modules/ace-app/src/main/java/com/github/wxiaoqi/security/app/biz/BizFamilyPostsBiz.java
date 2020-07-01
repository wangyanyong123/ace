package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.entity.BizFamilyPosts;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.activity.out.ApplyInfo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.topic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsVo;
import com.github.wxiaoqi.security.app.vo.topic.out.GradeRuleVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private BizCommunityTopicBiz bizCommunityTopicBiz;
    @Autowired
    private BizCommunityTopicMapper bizCommunityTopicMapper;
    @Autowired
    private BizForumPostsMapper bizForumPostsMapper;
    @Autowired
    private BizActivityMapper bizActivityMapper;
    @Autowired
    private BizActivityApplyMapper bizActivityApplyMapper;


    /**
     * 查询项目下的帖子活动
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<FamilyPostsVo>> getAllPostsList(String projectId, Integer page, Integer limit){
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String a1 = "1";//家里人帖子
        String a2 = "2";//议事厅-话题
        String a3 = "3";//议事厅-投票
        String a4 = "4";//邻里活动
        String a5 = "5";//社区话题
        String a6 = "6";//业主圈帖子
        String a7 = "7";//业主圈小组活动
        //查询用户加入的黑名单用户
        List<String> friendId = bizFamilyPostsMapper.selectFriendIdByUser(BaseContextHandler.getUserID());
        if(friendId == null || friendId.size() == 0){
            friendId = bizFamilyPostsMapper.selectUserIdByfriend(BaseContextHandler.getUserID());
        }
        List<FamilyPostsVo> postsVoList = bizFamilyPostsMapper.selectAllPostsList(projectId, BaseContextHandler.getUserID(),friendId,startIndex,limit);
        if(postsVoList != null && postsVoList.size() >0){
            //1-获取等级头衔
            for (FamilyPostsVo postsVo : postsVoList){
                if(a1.equals(postsVo.getTypeStr()) || a2.equals(postsVo.getTypeStr()) || a3.equals(postsVo.getTypeStr())){
                    String gradeTitle = "";
                    List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
                    if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
                        for (GradeRuleVo vo : gradeRuleVoList){
                            if(getGradeValue(postsVo.getUserId()) >= vo.getIntegral()){
                                gradeTitle = vo.getGradeTitle();
                            }
                        }
                        postsVo.setGradeTitle(gradeTitle);
                    }
                }else if(a5.equals(postsVo.getTypeStr())){
                    String userName = bizCommunityTopicMapper.selectUserNameByUserId(postsVo.getUserId());
                    if(userName != null && !StringUtils.isEmpty(userName)){
                        postsVo.setUserName(userName);
                        postsVo.setProfilePhoto("https://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/temp/20190904/978b2c0c284440bea28371228af8403e.png");
                    }
                }else if(a6.equals(postsVo.getTypeStr())){
                    //阅读人头像
                    List<ImgInfo> imgInfoList = bizForumPostsMapper.selectReaderPhoto(postsVo.getId());
                    if(imgInfoList == null || imgInfoList.size() == 0){
                        imgInfoList = new ArrayList<>();
                    }
                    postsVo.setImgList(imgInfoList);
                    //获取等级头衔
                    String gradeTitle = "";
                    List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
                    if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
                        for (GradeRuleVo vo : gradeRuleVoList){
                            if(getGradeValue(postsVo.getUserId()) >= vo.getIntegral()){
                                gradeTitle = vo.getGradeTitle();
                            }
                        }
                        postsVo.setGradeTitle(gradeTitle);
                    }
                }else if(a4.equals(postsVo.getTypeStr()) || a7.equals(postsVo.getTypeStr())){
                    //报活动名人头像
                    List<ImgInfo> imgInfoList = bizActivityMapper.selectApplyPhotoByThree(postsVo.getId());
                    for(ImgInfo url:imgInfoList){
                        if("".equals(url.getUrl())){
                            url.setUrl(getRandomPhoto());
                        }
                    }
                    postsVo.setImgList(imgInfoList);
                    //该活动是否收费
                    String isFree = bizActivityApplyMapper.selectIsFreeActivity(postsVo.getId());
                    //判断当前用户是否报名
                    ApplyInfo applyInfo = bizActivityApplyMapper.selectActivityApplyStatus(BaseContextHandler.getUserID(),postsVo.getId());
                    if("0".equals(isFree)){
                        //收费
                        if(applyInfo != null){
                            //报名成功
                            if("1".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                                postsVo.setApplyStatus("2");
                            }else if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                                postsVo.setApplyStatus("3");
                            }else{
                                postsVo.setApplyStatus("1");
                            }
                        }else{
                            //未报名
                            postsVo.setApplyStatus("1");
                            try {
                                if(Integer.parseInt(postsVo.getPersonNum()) != -1){
                                    if(Integer.parseInt(postsVo.getPersonNum()) <= postsVo.getApplyNum()){
                                        //报名已报满
                                        postsVo.setApplyStatus("6");
                                    }
                                }
                                if(sdf.parse(postsVo.getApplyEndTime()).getTime() < new Date().getTime()){
                                    //报名已截止
                                    postsVo.setApplyStatus("4");
                                }
                                if("3".equals(postsVo.getActivityStatus())){
                                    //活动已过期
                                    postsVo.setApplyStatus("5");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{//不收费
                        if(applyInfo != null){
                            if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                                //报名成功
                                postsVo.setApplyStatus("3");
                            }else{
                                postsVo.setApplyStatus("1");
                            }
                        }else{
                            //未报名
                            postsVo.setApplyStatus("1");
                            try {
                                if(Integer.parseInt(postsVo.getPersonNum()) != -1){
                                    if(Integer.parseInt(postsVo.getPersonNum()) <= postsVo.getApplyNum()){
                                        //报名已报满
                                        postsVo.setApplyStatus("6");
                                    }
                                }
                                if(sdf.parse(postsVo.getApplyEndTime()).getTime() < new Date().getTime()){
                                    //报名已截止
                                    postsVo.setApplyStatus("4");
                                }
                                if("3".equals(postsVo.getActivityStatus())){
                                    //活动已过期
                                    postsVo.setApplyStatus("5");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return ObjectRestResponse.ok(postsVoList);
    }

    /**
     * 获取随机头像
     * @return
     */
    private String getRandomPhoto() {
        String userPhoto = "";
        List<String> list = Stream.of("http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo3@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo4@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo5@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo6@2x.png").collect(toList());
        Random random = new Random();
        int n = random.nextInt(list.size());
        userPhoto = list.get(n);
        return userPhoto;
    }



    /**
     * 查询家里人帖子列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<FamilyPostsVo>> getFamilyPostsList(String projectId, Integer page, Integer limit){
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
        List<FamilyPostsVo> familyPostsVoList = bizFamilyPostsMapper.selectOldFamilyPostsList(projectId,"0", BaseContextHandler.getUserID(),startIndex,limit);
        if(familyPostsVoList != null && familyPostsVoList.size() >0){
            for (FamilyPostsVo postsVo : familyPostsVoList){
                String gradeTitle = "";
                List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
                if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
                    for (GradeRuleVo vo : gradeRuleVoList){
                        if(getGradeValue(postsVo.getUserId()) >= vo.getIntegral()){
                            gradeTitle = vo.getGradeTitle();
                        }
                    }
                    postsVo.setGradeTitle(gradeTitle);
                }
            }
        }
        return ObjectRestResponse.ok(familyPostsVoList);
    }


    /**
     * 查询好友的家里人帖子列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<FamilyPostsVo>> getMyFriendFamilyPostsList(String projectId, String userId, Integer page, Integer limit){
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
        String a1 = "1";//家里人帖子
        String a2 = "2";//议事厅-话题
        String a3 = "3";//议事厅-投票
        String a5 = "5";//社区话题
        String a6 = "6";//业主圈帖子
        List<FamilyPostsVo> familyPostsVoList = bizFamilyPostsMapper.selectFamilyPostsList(projectId, "1",userId,startIndex,limit);
        if(familyPostsVoList != null && familyPostsVoList.size() >0){
            for (FamilyPostsVo postsVo : familyPostsVoList){
                if(a1.equals(postsVo.getTypeStr()) || a2.equals(postsVo.getTypeStr()) || a3.equals(postsVo.getTypeStr())){
                    String gradeTitle = "";
                    List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
                    if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
                        for (GradeRuleVo vo : gradeRuleVoList){
                            if(getGradeValue(postsVo.getUserId()) >= vo.getIntegral()){
                                gradeTitle = vo.getGradeTitle();
                            }
                        }
                        postsVo.setGradeTitle(gradeTitle);
                    }
                }else if(a5.equals(postsVo.getTypeStr())){
                    String userName = bizCommunityTopicMapper.selectUserNameByUserId(postsVo.getUserId());
                    if(userName != null && !StringUtils.isEmpty(userName)){
                        postsVo.setUserName(userName);
                        postsVo.setProfilePhoto("https://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/temp/20190904/978b2c0c284440bea28371228af8403e.png");
                    }
                }else if(a6.equals(postsVo.getTypeStr())){
                    //阅读人头像
                    List<ImgInfo> imgInfoList = bizForumPostsMapper.selectReaderPhoto(postsVo.getId());
                    if(imgInfoList == null || imgInfoList.size() == 0){
                        imgInfoList = new ArrayList<>();
                    }
                    postsVo.setImgList(imgInfoList);
                }
            }
        }
        return ObjectRestResponse.ok(familyPostsVoList);
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
     * 查询家里人帖子详情
     * @param id
     * @return
     */
    public ObjectRestResponse<FamilyPostsInfo> getFamilyPostsInfo(final String id,final String projectId,String temp){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        //统计阅读数
        String str = "0";
        final String userId = BaseContextHandler.getUserID();
        if(str.equals(temp)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bizCommunityTopicBiz.addReaderDetail(id,projectId,"1",userId);
                }
            }).start();
        }
        FamilyPostsInfo familyPostsInfo = bizFamilyPostsMapper.selectFamilyPostsInfo(id,BaseContextHandler.getUserID());
        if(familyPostsInfo != null){
            String isShow = "0";
            if (isShow.equals(familyPostsInfo.getShowType())) {
                msg.setStatus(103);
                msg.setMessage("该帖已被隐藏,不支持查看详情!");
                return msg;
            }
            //判断用户当前角色
            CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
            if(userInfo != null){
                if("4".equals(userInfo.getIdentityType())){
                    familyPostsInfo.setIdentityType("1");
                }else{
                    familyPostsInfo.setIdentityType("0");
                }
            }
            if(bizCommunityTopicMapper.selectIsOperationByUserId(BaseContextHandler.getUserID()) > 0){
                familyPostsInfo.setIdentityType("2");
            }

        }
        return ObjectRestResponse.ok(familyPostsInfo);
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
        FamilyPostsInfo familyPostsInfo = bizFamilyPostsMapper.selectFamilyPostsInfo(param.getId(),BaseContextHandler.getUserID());
        if(familyPostsInfo != null) {
            String isShow = "0";
            if (isShow.equals(familyPostsInfo.getShowType())) {
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