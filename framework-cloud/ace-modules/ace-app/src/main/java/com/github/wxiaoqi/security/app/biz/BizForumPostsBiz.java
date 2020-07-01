package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.posts.in.*;
import com.github.wxiaoqi.security.app.vo.posts.out.*;
import com.github.wxiaoqi.security.app.vo.topic.in.SaveChamberTopicParam;
import com.github.wxiaoqi.security.app.vo.topic.in.SaveFamilyPostsParam;
import com.github.wxiaoqi.security.app.vo.topic.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.RuleThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.SensitiveUtil;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
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
 * 小组帖子表
 *
 * @author zxl
 * @Date 2018-12-18 10:45:45
 */
@Service
public class BizForumPostsBiz extends BusinessBiz<BizForumPostsMapper, BizForumPosts> {

    @Autowired
    private BizForumPostsMapper bizForumPostsMapper;
    @Autowired
    private BizGroupLeaderMapper bizGroupLeaderMapper;
    @Autowired
    private BizContentReaderDetailMapper bizContentReaderDetailMapper;
    @Autowired
    private BizContentReaderMapper bizContentReaderMapper;
    @Autowired
    private BizCommentMapper bizCommentMapper;
    @Autowired
    private BizOperatingDetailMapper bizOperatingDetailMapper;
    @Autowired
    private BizOperatingCountMapper bizOperatingCountMapper;
    @Autowired
    private BizSensitiveWordsMapper bizSensitiveWordsMapper;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private BizIntegralPersonalDetailBiz bizIntegralPersonalDetailBiz;
    @Autowired
    private BizFamilyPostsMapper bizFamilyPostsMapper;
    @Autowired
    private BizChamberTopicMapper bizChamberTopicMapper;
    @Autowired
    private BizChamberTopicSelectMapper bizChamberTopicSelectMapper;
    @Autowired
    private BizCommunityTopicBiz bizCommunityTopicBiz;
    @Autowired
    private BizCommunityTopicMapper bizCommunityTopicMapper;
    @Autowired
    private ToolFegin toolFegin;

    @Autowired
    BaseAppClientUserMapper baseAppClientUserMapper;

    /**
     * 查询帖子列表
     * @param groupId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<PostsVo>> getPostsList(String groupId, String postsType, Integer page, Integer limit){
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
        List<PostsVo> postsVoList = bizForumPostsMapper.selectPostsList(BaseContextHandler.getUserID(),groupId, postsType, startIndex, limit);
        for (PostsVo temp : postsVoList){
            //帖子评论数
            int commentNum = bizCommentMapper.selectCommentCount(temp.getId());
            temp.setCommentNum(commentNum);
            //阅读人头像
            List<ImgInfo> imgInfoList = bizForumPostsMapper.selectReaderPhoto(temp.getId());
            if(imgInfoList == null || imgInfoList.size() == 0){
                imgInfoList = new ArrayList<>();
            }
            temp.setImgList(imgInfoList);
        }
        if(postsVoList == null || postsVoList.size() == 0){
            postsVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(postsVoList);
    }



    /**
     * 查询帖子置顶列表
     * @param groupId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<PostsVo>> getTopPostsList(String groupId, Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if (page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<PostsVo> topPostsList = bizForumPostsMapper.selectTopPostsList(groupId, startIndex, limit);
        if(topPostsList == null || topPostsList.size() == 0){
            topPostsList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(topPostsList);
    }


    /**
     * 查询帖子详情
     * @param id
     * @return
     */
    public ObjectRestResponse<PostsInfo> getPostsInfo(final String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        PostsInfo postsInfo = bizForumPostsMapper.selectPostsInfo(id,BaseContextHandler.getUserID());

        if(postsInfo == null){
            postsInfo = new PostsInfo();
        }else{
            String isShow = "0";
            if (isShow.equals(postsInfo.getShowType())) {
                msg.setStatus(103);
                msg.setMessage("该帖已被隐藏,不支持任何操作!");
                return msg;
            }
            final String projectId = postsInfo.getProjectId();
            //统计阅读数
            final String userId = BaseContextHandler.getUserID();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bizCommunityTopicBiz.addReaderDetail(id,projectId,"4",userId);
                }
            }).start();
            //阅读详情加一
            BizContentReaderDetail detail = new BizContentReaderDetail();
            detail.setId(UUIDUtils.generateUuid());
            detail.setUserId(BaseContextHandler.getUserID());
            detail.setContentId(id);
            detail.setCreateBy(BaseContextHandler.getUserID());
            detail.setCreateTime(new Date());
            detail.setTimeStamp(new Date());
            if(bizContentReaderDetailMapper.insertSelective(detail) < 0){
                msg.setStatus(501);
                msg.setMessage("添加阅读详情记录失败!");
                return msg;
            }else{
                //阅读数加1
                ReaderInfo readerInfo = bizContentReaderMapper.selectReaderInfoByPostsId(id);
                //查询阅读的用户
                List<String> userList = bizContentReaderDetailMapper.selectUserByPostsId(id);
                if(readerInfo != null){//编辑阅读数
                    BizContentReader reader = new BizContentReader();
                    reader.setId(readerInfo.getId());
                    reader.setViewNum(readerInfo.getViewNum()+1);
                    if(userList != null && userList.size() > 0){
                        for(String user:userList){
                            if(!BaseContextHandler.getUserID().equals(user)){//判断当前用户是否已阅读该详情
                                reader.setUserNum(readerInfo.getUserNum()+1);
                            }
                        }
                    }
                    reader.setModifyTime(new Date());
                    reader.setModifyTy(BaseContextHandler.getUserID());
                    reader.setTimeStamp(new Date());
                    if(bizContentReaderMapper.updateByPrimaryKeySelective(reader) < 0){
                        msg.setStatus(502);
                        msg.setMessage("添加阅读数失败!");
                        return msg;
                    }
                }else{
                    //新插一条记录阅读数
                    BizContentReader reader = new BizContentReader();
                    reader.setId(UUIDUtils.generateUuid());
                    reader.setContentId(id);
                    reader.setViewNum(1);
                    reader.setUserNum(1);
                    reader.setCreateBy(BaseContextHandler.getUserID());
                    reader.setCreateTime(new Date());
                    reader.setTimeStamp(new Date());
                    if(bizContentReaderMapper.insertSelective(reader) < 0){
                        msg.setStatus(503);
                        msg.setMessage("添加阅读数失败!");
                        return msg;
                    }
                }
            }
            //判断用户是否点赞
            String isUserUp = bizOperatingDetailMapper.selectIsUpByUserId(BaseContextHandler.getUserID(),id);
            if(isUserUp != null){
                postsInfo.setIsUp("1");
            }else{
                postsInfo.setIsUp("0");
            }
            //帖子评论数
            int commentNum = bizCommentMapper.selectCommentCount(id);
            postsInfo.setCommentNum(commentNum);
            //判断当前登录用户所在小组中的角色是否是组长
            String groupId = bizForumPostsMapper.selectGroupIdById(id);
            String user = bizGroupLeaderMapper.selectIsGroupByUserId(groupId,BaseContextHandler.getUserID());
            if(user == null){
                postsInfo.setIsGroup("0");
            }else{
                postsInfo.setIsGroup("1");
            }
            //判断用户当前角色
            CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
            if(userInfo != null){
                if("4".equals(userInfo.getIdentityType())){
                    postsInfo.setIdentityType("1");
                }else{
                    postsInfo.setIdentityType("0");
                }
            }
            if(bizCommunityTopicMapper.selectIsOperationByUserId(BaseContextHandler.getUserID()) > 0){
                postsInfo.setIdentityType("2");
            }
        }
        return ObjectRestResponse.ok(postsInfo);
    }

    /**
     * 查询帖子评论列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<CommentTree>> getCommentList(String id, Integer page, Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if (page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        //评论列表
        List<CommentVo> commentVoList = bizCommentMapper.selectCommentList(BaseContextHandler.getUserID(),id,startIndex,limit);
        List<CommentTree> trees = new ArrayList<>();
        commentVoList.forEach(comment -> {
            //评论人头像
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(comment.getProfilePhoto());
            list.add(imgInfo);
            comment.setProfilePhotoList(list);
            trees.add(new CommentTree(comment.getId(), comment.getPid(),comment.getContent(),comment.getNickName(),
                    comment.getProfilePhotoList(), comment.getProfilePhoto(), comment.getCreateTime(),comment.getUpNum(),comment.getIsUp()));
        });
        List<CommentTree> commentList = TreeUtil.bulid(trees, "-1", null);
        if(commentList == null || commentList.size() == 0){
            commentList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(commentList);
    }

    //判断当前用户是否被禁止
    public ObjectRestResponse selectUserIsForbid(){
        ObjectRestResponse msg = new ObjectRestResponse();
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
        String data = "1";
        int count =  bizForumPostsMapper.selectIsForbidByUser(BaseContextHandler.getUserID());
        if(count > 0){
            data = "0";
        }
        msg.setData(data);
        return msg;
    }


    /**
     * 保存帖子
     * @param param
     * @return
     */
    public ObjectRestResponse savePosts(SavePostsParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
       int count =  bizForumPostsMapper.selectIsForbidByUser(BaseContextHandler.getUserID());
       if(count > 0){
           msg.setStatus(201);
           msg.setMessage("你已被举报,禁止发帖,请前往反馈处理!");
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
        if(param == null){
            msg.setStatus(202);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getDescription())){
            msg.setStatus(203);
            msg.setMessage("帖子内容不能为空!");
            return msg;
        }
        if(isContainSensitive(param.getTitle())){
            msg.setStatus(202);
            msg.setMessage("你的发帖包含违禁词!");
            return msg;
        }
        if(isContainSensitive(param.getDescription())){
            msg.setStatus(202);
            msg.setMessage("你的发帖包含违禁词!");
            return msg;
        }
        BizForumPosts posts = new BizForumPosts();
        posts.setId(UUIDUtils.generateUuid());
        posts.setGroupId(param.getGroupId());
        posts.setProjectId(param.getProjectId());
        posts.setUserId(BaseContextHandler.getUserID());
        posts.setTitle(param.getTitle());
        if(StringUtils.isNotEmpty(param.getPostImages())){
            ObjectRestResponse restResponse = toolFegin.moveAppUploadUrlPaths(param.getPostImages(), DocPathConstant.APPPOSTS);
            if(restResponse.getStatus()==200){
                posts.setPostImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
            }
        }
        posts.setDescription(param.getDescription());
        //posts.setPostImage(param.getPostImages());
        posts.setTimeStamp(new Date());
        posts.setCreateBy(BaseContextHandler.getUserID());
        posts.setCreateTime(new Date());
        if(bizForumPostsMapper.insertSelective(posts) < 0){
            msg.setStatus(204);
            msg.setMessage("发帖失败!");
            return msg;
        }
        //组员发帖,+10
        msg = bizIntegralPersonalDetailBiz.addPublicIntegarl(RuleThemeConstants.GROUP_POSTED,param.getGroupId(),"");
        //个人发帖,+5
        msg = bizIntegralPersonalDetailBiz.addPublicIntegarl(RuleThemeConstants.POSTED,"","");
        //个人首次发帖成功,+10
        msg = bizIntegralPersonalDetailBiz.addPublicIntegarl(RuleThemeConstants.FIRST_POSTED,"","");
        if(msg.getStatus() != 201 || msg.getStatus() != 202 || msg.getStatus() != 203 || msg.getStatus() != 204){
            msg.setStatus(200);
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 保存家里人帖子
     * @param param
     * @return
     */
    public ObjectRestResponse saveFamilyPosts(SaveFamilyPostsParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
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
        if(param == null){
            msg.setStatus(202);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getContent())){
            msg.setStatus(203);
            msg.setMessage("帖子内容不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getProjectId())){
            msg.setStatus(203);
            msg.setMessage("项目id不能为空!");
            return msg;
        }
        if(isContainSensitive(param.getContent())){
            msg.setStatus(202);
            msg.setMessage("你的发帖包含违禁词!");
            return msg;
        }
        BizFamilyPosts posts = new BizFamilyPosts();
        posts.setId(UUIDUtils.generateUuid());
        posts.setProjectId(param.getProjectId());
        posts.setUserId(BaseContextHandler.getUserID());
        posts.setContent(param.getContent());
        posts.setImageType(param.getImageType());
        if(StringUtils.isNotEmpty(param.getPostImages())){
            ObjectRestResponse restResponse = toolFegin.moveAppUploadUrlPaths(param.getPostImages(), DocPathConstant.APPPOSTS);
            if(restResponse.getStatus()==200){
                posts.setPostImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
            }
        }
        //posts.setPostImage(param.getPostImages());
        posts.setVideoImage(param.getVideoImage());
        posts.setTimeStamp(new Date());
        posts.setCreateBy(BaseContextHandler.getUserID());
        posts.setCreateTime(new Date());
        if(bizFamilyPostsMapper.insertSelective(posts) < 0){
            msg.setStatus(204);
            msg.setMessage("发帖失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 保存议事厅话题
     * @param param
     * @return
     */
    public ObjectRestResponse saveChamberTopic(SaveChamberTopicParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
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
        if(param == null){
            msg.setStatus(202);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getTopicType())){
            msg.setStatus(203);
            msg.setMessage("议事类型不能为空!");
            return msg;
        }
        String type = "1";
        if(type.equals(param.getTopicType())){
            if(StringUtils.isEmpty(param.getTagId())){
                msg.setStatus(203);
                msg.setMessage("话题标签不能为空!");
                return msg;
            }
        }else{
            if(StringUtils.isEmpty(param.getEndTime())){
                msg.setStatus(203);
                msg.setMessage("截止时间不能为空!");
                return msg;
            }
            if(param.getSelectVo() == null || param.getSelectVo().length == 0){
                msg.setStatus(203);
                msg.setMessage("投票选项不能为空!");
                return msg;
            }
        }
        if(StringUtils.isEmpty(param.getContent())){
            msg.setStatus(203);
            msg.setMessage("话题内容/投票标题不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getProjectId())){
            msg.setStatus(203);
            msg.setMessage("项目id不能为空!");
            return msg;
        }
        if(isContainSensitive(param.getContent())){
            msg.setStatus(202);
            msg.setMessage("你的发帖包含违禁词!");
            return msg;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        BizChamberTopic topic = new BizChamberTopic();
        String id = UUIDUtils.generateUuid();
        topic.setId(id);
        topic.setUserId(BaseContextHandler.getUserID());
        topic.setProjectId(param.getProjectId());
        topic.setTopicType(param.getTopicType());
        if (type.equals(param.getTopicType())) {
            topic.setContent(param.getContent());
            topic.setTopicTagId(param.getTagId());
            if(StringUtils.isNotEmpty(param.getTopicImages())){
                ObjectRestResponse restResponse = toolFegin.moveAppUploadUrlPaths(param.getTopicImages(), DocPathConstant.APPPOSTS);
                if(restResponse.getStatus()==200){
                    topic.setTopicImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                }
            }
            //topic.setTopicImage(param.getTopicImages());
        }else{
            try {
                topic.setTitle(param.getContent());
                topic.setSelection(param.getSelection());
                topic.setEndtime(sdf.parse(param.getEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        topic.setTimeStamp(new Date());
        topic.setCreateBy(BaseContextHandler.getUserID());
        topic.setCreateTime(new Date());
        if(bizChamberTopicMapper.insertSelective(topic) < 0){
            msg.setStatus(204);
            msg.setMessage("发帖失败!");
            return msg;
        }else{
            if(!type.equals(param.getTopicType())){
                //保存投票选项
                String[] selectVo = param.getSelectVo();
                for (int i = 0; i < selectVo.length; i++) {
                    BizChamberTopicSelect select = new BizChamberTopicSelect();
                    select.setId(UUIDUtils.generateUuid());
                    select.setTopicId(id);
                    select.setSelectContent(selectVo[i]);
                    select.setSort(i);
                    select.setTimeStamp(new Date());
                    select.setCreateBy(BaseContextHandler.getUserID());
                    select.setCreateTime(new Date());
                    bizChamberTopicSelectMapper.insertSelective(select);
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     *  帖子操作置顶,隐藏,精华
     * @param param
     * @return
     */
    public ObjectRestResponse updatePostsStatus(UpdatePostsStatus param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(501);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        PostsInfo postsInfo = bizForumPostsMapper.selectPostsInfo(param.getId(),BaseContextHandler.getUserID());
        if(postsInfo != null){
            String isShow = "0";
            if (isShow.equals(postsInfo.getShowType())) {
                msg.setStatus(103);
                msg.setMessage("该帖已被隐藏,不支持任何操作!");
                return msg;
            }
        }
        /*String groupId = bizForumPostsMapper.selectGroupIdById(param.getId());
        //判断当前登录用户所在小组中的角色是否是组长
       String user = bizGroupLeaderMapper.selectIsGroupByUserId(groupId,BaseContextHandler.getUserID());
        if(user == null){
            msg.setStatus(502);
            msg.setMessage("当前用户不是组长角色,不能进行该操作!");
            return msg;
        }*/
        if(bizForumPostsMapper.updatePostsStatusById(param.getStatus(),param.getId(),BaseContextHandler.getUserID()) < 0){
            msg.setStatus(503);
            msg.setMessage("操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 保存帖子评论
     * @param param
     * @return
     */
    public ObjectRestResponse saveComment(SaveCommentParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(param.getContent())){
            msg.setStatus(202);
            msg.setMessage("请输入评论内容!");
            return msg;
        }
        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        String a4 = "4";
        String a5 = "5";
        String isShow = "0";
        String showType = "0";
        if(a5.equals(param.getType())){
            CommunityTopicInfo communityTopicInfo = bizCommunityTopicMapper.selectCommunityTopicInfo(param.getObjectId(),BaseContextHandler.getUserID());
            if(communityTopicInfo != null) {
                showType = communityTopicInfo.getShowType();
            }
        }else if(a3.equals(param.getType())){
            FamilyPostsInfo familyPostsInfo =  bizFamilyPostsMapper.selectFamilyPostsInfo(param.getObjectId(),BaseContextHandler.getUserID());
            if(familyPostsInfo != null) {
                showType = familyPostsInfo.getShowType();
            }
        }else if(a4.equals(param.getType())){
            ChamberTopicInfo chamberTopicInfo = bizChamberTopicMapper.selectChamberTopicInfo(param.getObjectId(),BaseContextHandler.getUserID());
            if(chamberTopicInfo != null) {
                showType = chamberTopicInfo.getShowType();
            }
        }else if(a1.equals(param.getType())){
            PostsInfo postsInfo = bizForumPostsMapper.selectPostsInfo(param.getObjectId(),BaseContextHandler.getUserID());
            if(postsInfo != null) {
                showType = postsInfo.getShowType();
            }
        }else{
            showType = "1";
        }
        if (isShow.equals(showType)) {
            msg.setStatus(103);
            msg.setMessage("该帖已被隐藏,不支持评论!");
            return msg;
        }

        int count =  bizForumPostsMapper.selectIsForbidByUser(BaseContextHandler.getUserID());
        if(count > 0){
            msg.setStatus(201);
            msg.setMessage("你已被举报,禁止评论,请前往反馈处理!");
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
        if(isContainSensitive(param.getContent())){
            msg.setStatus(202);
            msg.setMessage("你的评论包含违禁词,请重新评论!");
            return msg;
        }
        String groupId = bizForumPostsMapper.selectGroupIdById(param.getObjectId());
        BizComment comment = new BizComment();
        comment.setId(UUIDUtils.generateUuid());
        comment.setObjectId(param.getObjectId());
        comment.setGroupId(groupId);
        comment.setUserId(BaseContextHandler.getUserID());
        if("1".equals(param.getCommentType())){
            comment.setPid("-1");
        }else{
            comment.setPid(param.getCommentId());
        }
        comment.setContent(param.getContent());
        comment.setType(param.getType());
        comment.setCreateBy(BaseContextHandler.getUserID());
        comment.setCreateTime(new Date());
        comment.setTimeStamp(new Date());
        if(bizCommentMapper.insertSelective(comment) < 0){
            msg.setStatus(503);
            msg.setMessage("评论帖子失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 隐藏评论操作
     * @param commentId
     * @return
     */
    public ObjectRestResponse hideComment(String commentId){
        ObjectRestResponse msg = new ObjectRestResponse();
        String groupId = bizCommentMapper.selectGroupIdById(commentId);
        //判断当前登录用户所在小组中的角色是否是组长
        String user = bizGroupLeaderMapper.selectIsGroupByUserId(groupId,BaseContextHandler.getUserID());
        if(user == null){
            msg.setStatus(502);
            msg.setMessage("当前用户不是组长角色,不能进行该操作!");
            return msg;
        }
        if(bizCommentMapper.updateCommentTypeById(commentId,BaseContextHandler.getUserID())> 0){
            bizCommentMapper.updatePcommentTypeByPId(commentId,BaseContextHandler.getUserID());
        }else{
            msg.setStatus(503);
            msg.setMessage("隐藏评论失败!");
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
                    //帖子表up_num 加1
                    int postsLikeNum =  bizForumPostsMapper.selectUpNumByPostsId(param.getObjectId());
                    BizForumPosts posts = new BizForumPosts();
                    posts.setId(param.getObjectId());
                    posts.setUpNum(postsLikeNum+1);
                    posts.setModifyTime(new Date());
                    posts.setModifyBy(BaseContextHandler.getUserID());
                    posts.setTimeStamp(new Date());
                    if(bizForumPostsMapper.updateByPrimaryKeySelective(posts) < 0){
                        msg.setStatus(504);
                        msg.setMessage("帖子点赞操作失败!");
                        return msg;
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
                    //帖子表up_num 减1
                    int postsLikeNum =  bizForumPostsMapper.selectUpNumByPostsId(param.getObjectId());
                    BizForumPosts posts = new BizForumPosts();
                    posts.setId(param.getObjectId());
                    posts.setUpNum(postsLikeNum-1);
                    posts.setModifyTime(new Date());
                    posts.setModifyBy(BaseContextHandler.getUserID());
                    posts.setTimeStamp(new Date());
                    if(bizForumPostsMapper.updateByPrimaryKeySelective(posts) < 0){
                        msg.setStatus(504);
                        msg.setMessage("帖子取消点赞操作失败!");
                        return msg;
                    }
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }



    /**
     * 分享帖子详情
     * @param id
     * @return
     */
    public ObjectRestResponse<PostsInfo> sharePostsInfo(String id){
        PostsInfo postsInfo = bizForumPostsMapper.selectPostsInfo(id,BaseContextHandler.getUserID());
        if(postsInfo == null){
            postsInfo = new PostsInfo();
        }else{
            //帖子评论数
            int commentNum = bizCommentMapper.selectCommentCount(id);
            postsInfo.setCommentNum(commentNum);
            //分享,+5
            //bizIntegralPersonalDetailBiz.addPublicIntegarl(RuleThemeConstants.SHARE,"","");
        }
        return ObjectRestResponse.ok(postsInfo);
    }

    /**
     * 分享帖子评论列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<CommentTree>> shareCommentList(String id, Integer page, Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if (page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        //评论列表
        List<CommentVo> commentVoList = bizCommentMapper.shareCommentList(id,startIndex,limit);
        List<CommentTree> trees = new ArrayList<>();
        commentVoList.forEach(comment -> {
            //评论人头像
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(comment.getProfilePhoto());
            list.add(imgInfo);
            comment.setProfilePhotoList(list);
            trees.add(new CommentTree(comment.getId(), comment.getPid(),comment.getContent(),comment.getNickName(),
                    comment.getProfilePhotoList(), comment.getProfilePhoto(), comment.getCreateTime(),comment.getUpNum(),comment.getIsUp()));
        });
        List<CommentTree> commentList = TreeUtil.bulid(trees, "-1", null);
        if(commentList == null || commentList.size() == 0){
            commentList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(commentList);
    }







    //查询敏感词
    public String selectSensitiveWors() {
        return StringUtils.join(bizSensitiveWordsMapper.getSensitiveWordList(), ",");
    }


    /**
     * 判断是否包含敏感词
     * @param content
     * @return
     */
    public boolean isContainSensitive(String content) {
       /* if (SensitiveUtil.getTree() == null || SensitiveUtil.getTree().size() < 1) {
            String words = selectSensitiveWors();
            SensitiveUtil.init(words);
        }*/
        String words = selectSensitiveWors();
        SensitiveUtil.init(words);
        ArrayList<String> checkWords = SensitiveUtil.checkWords(content);
        if (checkWords != null && checkWords.size() > 0) {
            return true;
        }
        return false;
    }



    /**
     * 查询我的帖子
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<FamilyPostsVo>> getUserPostsList(Integer page, Integer limit){
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
        String a6 = "6";//业主圈帖子
//        List<FamilyPostsVo> postsVoList = bizForumPostsMapper.selectPostsListByUser(BaseContextHandler.getUserID(), startIndex,limit);
        List<FamilyPostsVo> postsVoList = bizForumPostsMapper.selectNewPostsListByUser(BaseContextHandler.getUserID(), startIndex,limit);
        for (FamilyPostsVo postsVo : postsVoList){
            BaseAppClientUser baseAppClientUser = baseAppClientUserMapper.selectByPrimaryKey(postsVo.getUserId());
            if (baseAppClientUser != null) {
                postsVo.setUserName(baseAppClientUser.getNickname());
                postsVo.setProfilePhoto(baseAppClientUser.getProfilePhoto());
                postsVo.setSex(baseAppClientUser.getSex());
            }
            String isUserUp = bizOperatingDetailMapper.selectIsUpByUserId(postsVo.getUserId(), postsVo.getId());
            if(isUserUp != null){
                postsVo.setIsUp("1");
            }else{
                postsVo.setIsUp("0");
            }

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
            }else if(a6.equals(postsVo.getTypeStr())){
                //阅读人头像
                List<ImgInfo> imgInfoList = bizForumPostsMapper.selectReaderPhoto(postsVo.getId());
                List<ImgInfo> imglist = new ArrayList<>();
                if(imgInfoList != null && imgInfoList.size() >0){
                    for (ImgInfo itemp : imgInfoList) {
                        ImgInfo imgPhotoInfo = new ImgInfo();
                        if (itemp.getUrl() !=null && !"".equals(itemp.getUrl())) {
                            imgPhotoInfo.setUrl(itemp.getUrl());
                        }else {
                            imgPhotoInfo.setUrl(getRandomPhoto());
                        }
                        imglist.add(imgPhotoInfo);
                    }
                }else{
                    postsVo.setImgList(new ArrayList<>());
                }
                if (imgInfoList.size() < 3 && postsVo.getViewNum() > imgInfoList.size()) {
                    ImgInfo imgPhotoInfo = new ImgInfo();
                    if (imgInfoList.size() == 1  && postsVo.getViewNum() >= 3 ) {
                        for (int i = 0; i < 2; i++) {
                            imgPhotoInfo.setUrl(getRandomPhoto());
                            imglist.add(imgPhotoInfo);
                        }
                    } else {
                        imgPhotoInfo.setUrl(getRandomPhoto());
                        imglist.add(imgPhotoInfo);
                    }
                }
                postsVo.setImgList(imglist);
            }
        }
        if(postsVoList == null || postsVoList.size() == 0){
            postsVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(postsVoList);
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
     * 删帖
     * @param param
     * @return
     */
    public ObjectRestResponse deletePostsById(DeletePostsParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(502);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getId())){
            msg.setStatus(202);
            msg.setMessage("id不能为空!");
            return msg;
        }
        String a1 = "1";//家里人帖子
        String a2 = "2";//议事厅-话题
        String a3 = "3";//议事厅-投票
        String a6 = "6";//业主圈帖子
        int num = 0;
        if(a1.equals(param.getTypeStr())){
            FamilyPostsInfo familyPostsInfo =  bizFamilyPostsMapper.selectFamilyPostsInfo(param.getId(), BaseContextHandler.getUserID());
            if(familyPostsInfo != null){
                if(!param.getUserId().equals(familyPostsInfo.getUserId())){
                    msg.setStatus(202);
                    msg.setMessage("不可删除它人的帖子!");
                    return msg;
                }
            }
           num = bizForumPostsMapper.delFamilyPostsById(param.getId(),BaseContextHandler.getUserID());
        }else if(a2.equals(param.getTypeStr()) || a3.equals(param.getTypeStr())){
            ChamberTopicInfo chamberTopicInfo = bizChamberTopicMapper.selectChamberTopicInfo(param.getId(),BaseContextHandler.getUserID());
            if(chamberTopicInfo != null){
                if(!param.getUserId().equals(chamberTopicInfo.getUserId())){
                    msg.setStatus(202);
                    msg.setMessage("不可删除它人的帖子!");
                    return msg;
                }
            }
            num = bizForumPostsMapper.delChamberPostsById(param.getId(),BaseContextHandler.getUserID());
        }else if(a6.equals(param.getTypeStr())){
            PostsInfo postsInfo =  bizForumPostsMapper.selectPostsInfo(param.getId(),BaseContextHandler.getUserID());
            if(postsInfo != null){
                if(!param.getUserId().equals(postsInfo.getUserId())){
                    msg.setStatus(202);
                    msg.setMessage("不可删除它人的帖子!");
                    return msg;
                }
            }
            num = bizForumPostsMapper.delPostsById(param.getId(),BaseContextHandler.getUserID());
        }
        if(num <= 0){
            msg.setStatus(202);
            msg.setMessage("删帖失败!");
            return msg;
        }
        return msg;
    }



}