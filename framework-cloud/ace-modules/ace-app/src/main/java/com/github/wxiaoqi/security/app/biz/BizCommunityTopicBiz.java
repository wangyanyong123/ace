package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.posts.in.SaveLikeParam;
import com.github.wxiaoqi.security.app.vo.posts.out.LikeInfo;
import com.github.wxiaoqi.security.app.vo.topic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.app.vo.topic.out.ChamberTopicInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.CommunityTopicInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 社区话题表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Service
public class BizCommunityTopicBiz extends BusinessBiz<BizCommunityTopicMapper,BizCommunityTopic> {

    @Autowired
    private BizCommunityTopicMapper bizCommunityTopicMapper;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private BizOperatingDetailMapper bizOperatingDetailMapper;
    @Autowired
    private BizOperatingCountMapper bizOperatingCountMapper;
    @Autowired
    private BizCommentMapper bizCommentMapper;
    @Autowired
    private BizFamilyPostsMapper bizFamilyPostsMapper;
    @Autowired
    private BizChamberTopicMapper bizChamberTopicMapper;
    @Autowired
    private BizReaderDetailMapper bizReaderDetailMapper;

    /**
     * 查询社区话题列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<CommunityTopicVo>> getCommunityTopicList(String projectId, Integer page, Integer limit){
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
        List<CommunityTopicVo> communityTopicVoList = bizCommunityTopicMapper.selectCommunityTopicList(projectId, BaseContextHandler.getUserID(),
                startIndex, limit);
        if(communityTopicVoList == null || communityTopicVoList.size() == 0){
            communityTopicVoList = new ArrayList<>();
        }else{
            for (CommunityTopicVo topicVo: communityTopicVoList){
                String userName = bizCommunityTopicMapper.selectUserNameByUserId(topicVo.getUserId());
                if(userName != null && !StringUtils.isEmpty(userName)){
                    topicVo.setUserName(userName);
                    topicVo.setProfilePhoto("https://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/temp/20190904/978b2c0c284440bea28371228af8403e.png");
                }
            }
        }
        return ObjectRestResponse.ok(communityTopicVoList);
    }

    /**
     * 查询社区话题详情
     * @param id
     * @return
     */
    public ObjectRestResponse<CommunityTopicInfo> getCommunityTopicInfo(final String id, final String projectId,String temp){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        String str = "0";
        if(str.equals(temp)){
            //统计阅读数
            final String userId = BaseContextHandler.getUserID();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    addReaderDetail(id,projectId,"3",userId);
                }
            }).start();
        }
        CommunityTopicInfo communityTopicInfo = bizCommunityTopicMapper.selectCommunityTopicInfo(id,BaseContextHandler.getUserID());
        if(communityTopicInfo != null){
            String isShow = "0";
            if(isShow.equals(communityTopicInfo.getShowType())){
                msg.setStatus(103);
                msg.setMessage("该帖已被隐藏,不支持查看详情!");
                return msg;
            }
            //判断用户当前角色
            CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
            if(userInfo != null){
                if("4".equals(userInfo.getIdentityType())){
                    communityTopicInfo.setIdentityType("1");
                }else{
                    communityTopicInfo.setIdentityType("0");
                }
            }
            if(bizCommunityTopicMapper.selectIsOperationByUserId(BaseContextHandler.getUserID()) > 0){
                communityTopicInfo.setIdentityType("2");
            }
            if(communityTopicInfo.getProfilePhoto() == null || StringUtils.isEmpty(communityTopicInfo.getProfilePhoto())){
                communityTopicInfo.setProfilePhoto("https://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/temp/20190904/978b2c0c284440bea28371228af8403e.png");
            }
            String userName = bizCommunityTopicMapper.selectUserNameByUserId(communityTopicInfo.getUserId());
            if(userName != null && !StringUtils.isEmpty(userName)){
                communityTopicInfo.setUserName(userName);
            }

        }
        return ObjectRestResponse.ok(communityTopicInfo);
    }


    public void addReaderDetail(String id, String projectId,String type,String userId){
        BizReaderDetail detail = new BizReaderDetail();
        detail.setId(UUIDUtils.generateUuid());
        detail.setObjectId(id);
        detail.setProjectId(projectId);
        detail.setType(type);
        detail.setUserId(userId);
        detail.setTimeStamp(new Date());
        detail.setCreateBy(userId);
        detail.setCreateTime(new Date());
        bizReaderDetailMapper.insertSelective(detail);
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
     *  社区话题操作置顶,隐藏
     * @param param
     * @return
     */
    public ObjectRestResponse updateCommunityTopicStatus(UpdateTopicStatus param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(501);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        CommunityTopicInfo communityTopicInfo = bizCommunityTopicMapper.selectCommunityTopicInfo(param.getId(),BaseContextHandler.getUserID());
        if(communityTopicInfo != null) {
            String isShow = "0";
            if (isShow.equals(communityTopicInfo.getShowType())) {
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
        if(bizCommunityTopicMapper.updateCommunityTopicStatusById(param.getStatus(),param.getId(), BaseContextHandler.getUserID()) < 0){
            msg.setStatus(503);
            msg.setMessage("操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }




    /**
     * 用户点赞与取消点赞操作
     * @param param
     * @return
     */
    public ObjectRestResponse saveLikeOperating(SaveLikeParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        String isShow = "0";
        String showType = "0";
        String objectId = null;
        if(a1.equals(param.getUpType())){
            objectId = bizCommentMapper.selectObjectIdBycommentId(param.getObjectId());
        }else{
            objectId = param.getObjectId();
        }
        if(a1.equals(param.getOperatType())){
            CommunityTopicInfo communityTopicInfo = bizCommunityTopicMapper.selectCommunityTopicInfo(objectId,BaseContextHandler.getUserID());
            if(communityTopicInfo != null) {
                showType = communityTopicInfo.getShowType();
            }
        }else if(a2.equals(param.getOperatType())){
            FamilyPostsInfo familyPostsInfo =  bizFamilyPostsMapper.selectFamilyPostsInfo(objectId,BaseContextHandler.getUserID());
            if(familyPostsInfo != null) {
                showType = familyPostsInfo.getShowType();
            }
        }else if(a3.equals(param.getOperatType())){
            ChamberTopicInfo chamberTopicInfo = bizChamberTopicMapper.selectChamberTopicInfo(objectId,BaseContextHandler.getUserID());
            if(chamberTopicInfo != null) {
                showType = chamberTopicInfo.getShowType();
            }
        }
        if (isShow.equals(showType)) {
            msg.setStatus(103);
            msg.setMessage("该帖已被隐藏,不支持任何操作!");
            return msg;
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
        BizOperatingDetail detail = new BizOperatingDetail();
        //点赞
        if("1".equals(param.getType())){
            //判断用户是否点赞
            String isUserUp = bizOperatingDetailMapper.selectIsUpByUserId(BaseContextHandler.getUserID(),param.getObjectId());
            if(isUserUp != null){
                msg.setStatus(501);
                msg.setMessage("已点赞!");
                return msg;
            }
            detail.setId(UUIDUtils.generateUuid());
            detail.setObjectId(param.getObjectId());
            detail.setUserId(BaseContextHandler.getUserID());
            detail.setOperating(param.getOperating());
            detail.setCreateBy(BaseContextHandler.getUserID());
            detail.setCreateTime(new Date());
            detail.setTimeStamp(new Date());
            if(bizOperatingDetailMapper.insertSelective(detail) < 0){
                msg.setStatus(503);
                msg.setMessage("点赞操作失败!");
                return msg;
            }else{
                //点赞数加一
                LikeInfo likeInfo = bizOperatingCountMapper.selectLikeNumByPostsId(param.getObjectId());
                if(likeInfo != null){
                    //点赞数加一
                    BizOperatingCount operating = new BizOperatingCount();
                    operating.setId(likeInfo.getId());
                    operating.setLikeNum(likeInfo.getLikeNum()+1);
                    operating.setModifyBy(BaseContextHandler.getUserID());
                    operating.setModifyTime(new Date());
                    operating.setTimeStamp(new Date());
                    if(bizOperatingCountMapper.updateByPrimaryKeySelective(operating) < 0){
                        msg.setStatus(503);
                        msg.setMessage("点赞操作失败!");
                        return msg;
                    }
                }else{
                    //插入点赞记录
                    BizOperatingCount operating = new BizOperatingCount();
                    operating.setId(UUIDUtils.generateUuid());
                    operating.setObjectId(param.getObjectId());
                    operating.setLikeNum(1);
                    operating.setCreateBy(BaseContextHandler.getUserID());
                    operating.setCreateTime(new Date());
                    operating.setTimeStamp(new Date());
                    if(bizOperatingCountMapper.insertSelective(operating) < 0){
                        msg.setStatus(503);
                        msg.setMessage("点赞操作失败!");
                        return msg;
                    }
                }
                if("1".equals(param.getUpType())){
                    //评论表up_num 加1
                    int commentLikeNum = bizCommentMapper.selectUpNumById(param.getObjectId());
                    BizComment comment = new BizComment();
                    comment.setId(param.getObjectId());
                    comment.setUpNum(commentLikeNum+1);
                    comment.setModifyTime(new Date());
                    comment.setModifyBy(BaseContextHandler.getUserID());
                    comment.setTimeStamp(new Date());
                    if(bizCommentMapper.updateByPrimaryKeySelective(comment) < 0){
                        msg.setStatus(503);
                        msg.setMessage("评论点赞操作失败!");
                        return msg;
                    }
                }else if("2".equals(param.getUpType())){
                    if("1".equals(param.getOperatType())){
                        //社区话题表up_num 加1
                        int postsLikeNum =  bizCommunityTopicMapper.selectUpNumByTopicId(param.getObjectId());
                        BizCommunityTopic topic = new BizCommunityTopic();
                        topic.setId(param.getObjectId());
                        topic.setUpNum(postsLikeNum+1);
                        topic.setModifyTime(new Date());
                        topic.setModifyBy(BaseContextHandler.getUserID());
                        topic.setTimeStamp(new Date());
                        if(bizCommunityTopicMapper.updateByPrimaryKeySelective(topic) < 0){
                            msg.setStatus(504);
                            msg.setMessage("帖子点赞操作失败!");
                            return msg;
                        }
                    }else if("2".equals(param.getOperatType())){
                        //家里人帖子表up_num 加1
                        int postsLikeNum =  bizFamilyPostsMapper.selectUpNumByTopicId(param.getObjectId());
                        BizFamilyPosts posts = new BizFamilyPosts();
                        posts.setId(param.getObjectId());
                        posts.setUpNum(postsLikeNum+1);
                        posts.setModifyTime(new Date());
                        posts.setModifyBy(BaseContextHandler.getUserID());
                        posts.setTimeStamp(new Date());
                        if(bizFamilyPostsMapper.updateByPrimaryKeySelective(posts) < 0){
                            msg.setStatus(504);
                            msg.setMessage("帖子点赞操作失败!");
                            return msg;
                        }
                    }else if("3".equals(param.getOperatType())){
                        //议事厅话题表up_num 加1
                        int postsLikeNum =  bizChamberTopicMapper.selectUpNumByTopicId(param.getObjectId());
                        BizChamberTopic topic = new BizChamberTopic();
                        topic.setId(param.getObjectId());
                        topic.setUpNum(postsLikeNum+1);
                        topic.setModifyTime(new Date());
                        topic.setModifyBy(BaseContextHandler.getUserID());
                        topic.setTimeStamp(new Date());
                        if(bizChamberTopicMapper.updateByPrimaryKeySelective(topic) < 0){
                            msg.setStatus(504);
                            msg.setMessage("帖子点赞操作失败!");
                            return msg;
                        }
                    }
                }
            }
        }else{
            //取消点赞
            int total = bizOperatingDetailMapper.delLikeDetail(BaseContextHandler.getUserID(),param.getObjectId());
            if(total == 0){
                msg.setStatus(509);
                msg.setMessage("已取消点赞!");
                return msg;
            }
            //点赞数减1
            LikeInfo likeInfo = bizOperatingCountMapper.selectLikeNumByPostsId(param.getObjectId());
            BizOperatingCount operating = new BizOperatingCount();
            operating.setId(likeInfo.getId());
            operating.setLikeNum(likeInfo.getLikeNum()-1);
            operating.setModifyBy(BaseContextHandler.getUserID());
            operating.setModifyTime(new Date());
            operating.setTimeStamp(new Date());
            if(bizOperatingCountMapper.updateByPrimaryKeySelective(operating) < 0){
                msg.setStatus(502);
                msg.setMessage("取消点赞操作失败!");
                return msg;
            }else{
                if("1".equals(param.getUpType())){
                    //评论表up_num 减1
                    int commentLikeNum = bizCommentMapper.selectUpNumById(param.getObjectId());
                    BizComment comment = new BizComment();
                    comment.setId(param.getObjectId());
                    comment.setUpNum(commentLikeNum-1);
                    comment.setModifyTime(new Date());
                    comment.setModifyBy(BaseContextHandler.getUserID());
                    comment.setTimeStamp(new Date());
                    if(bizCommentMapper.updateByPrimaryKeySelective(comment) < 0){
                        msg.setStatus(503);
                        msg.setMessage("评论取消点赞操作失败!");
                        return msg;
                    }
                }else if("2".equals(param.getUpType())){
                    if("1".equals(param.getOperatType())){
                        //社区话题表up_num 减1
                        int postsLikeNum =  bizCommunityTopicMapper.selectUpNumByTopicId(param.getObjectId());
                        BizCommunityTopic tpoic = new BizCommunityTopic();
                        tpoic.setId(param.getObjectId());
                        tpoic.setUpNum(postsLikeNum-1);
                        tpoic.setModifyTime(new Date());
                        tpoic.setModifyBy(BaseContextHandler.getUserID());
                        tpoic.setTimeStamp(new Date());
                        if(bizCommunityTopicMapper.updateByPrimaryKeySelective(tpoic) < 0){
                            msg.setStatus(504);
                            msg.setMessage("帖子取消点赞操作失败!");
                            return msg;
                        }
                    }else if("2".equals(param.getOperatType())){
                        //家里人帖子表up_num 减1
                        int postsLikeNum =  bizFamilyPostsMapper.selectUpNumByTopicId(param.getObjectId());
                        BizFamilyPosts posts = new BizFamilyPosts();
                        posts.setId(param.getObjectId());
                        posts.setUpNum(postsLikeNum-1);
                        posts.setModifyTime(new Date());
                        posts.setModifyBy(BaseContextHandler.getUserID());
                        posts.setTimeStamp(new Date());
                        if(bizFamilyPostsMapper.updateByPrimaryKeySelective(posts) < 0){
                            msg.setStatus(504);
                            msg.setMessage("帖子取消点赞操作失败!");
                            return msg;
                        }
                    }else if("3".equals(param.getOperatType())){
                        //议事厅话题表up_num 减1
                        int postsLikeNum =  bizChamberTopicMapper.selectUpNumByTopicId(param.getObjectId());
                        BizChamberTopic topic = new BizChamberTopic();
                        topic.setId(param.getObjectId());
                        topic.setUpNum(postsLikeNum-1);
                        topic.setModifyTime(new Date());
                        topic.setModifyBy(BaseContextHandler.getUserID());
                        topic.setTimeStamp(new Date());
                        if(bizChamberTopicMapper.updateByPrimaryKeySelective(topic) < 0){
                            msg.setStatus(504);
                            msg.setMessage("帖子点赞操作失败!");
                            return msg;
                        }
                    }

                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }






}