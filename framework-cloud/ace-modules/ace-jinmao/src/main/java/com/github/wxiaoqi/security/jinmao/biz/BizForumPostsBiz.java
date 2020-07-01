package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.entity.BizForumPosts;
import com.github.wxiaoqi.security.jinmao.mapper.BizCommentMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizForumPostsMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.vo.fosts.in.UpdateCommentStatus;
import com.github.wxiaoqi.security.jinmao.vo.fosts.in.UpdatePostsStatus;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.CommentVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.FostsVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.GroupInfo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.PostsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 小组帖子表
 *
 * @author huangxl
 * @Date 2019-01-28 15:06:24
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizForumPostsBiz extends BusinessBiz<BizForumPostsMapper,BizForumPosts> {

    @Autowired
    private BizForumPostsMapper bizForumPostsMapper;
    @Autowired
    private BizCommentMapper bizCommentMapper;
    @Autowired
    private BizProductMapper bizProductMapper;

    /**
     * 查询帖子列表
     * @param projectId
     * @param groupId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<FostsVo> getPostsList(String projectId, String isTop,String groupId, String startTime, String endTime,
                                                          String searchVal, Integer page, Integer limit){
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
        List<FostsVo> postsList = bizForumPostsMapper.selectFostsList(projectId,isTop, groupId, startTime, endTime, searchVal, startIndex, limit);
        if(postsList == null || postsList.size() == 0){
            postsList = new ArrayList<>();
        }
        return postsList;
    }


    /**
     * 查询帖子列表数量
     * @param projectId
     * @param groupId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    public int selectPostsCount(String projectId,String isTop, String groupId, String startTime, String endTime,
                                String searchVal){
        return bizForumPostsMapper.selectPostsCount(projectId,isTop, groupId, startTime, endTime, searchVal);
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
        if(bizForumPostsMapper.updatePostsStatusById(param.getStatus(),param.getId(), BaseContextHandler.getUserID()) < 0){
            msg.setStatus(503);
            msg.setMessage("操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询帖子详情
     * @param id
     * @return
     */
    public List<PostsInfo> getPostsInfoById(String id){
        List<PostsInfo> result = new ArrayList<>();
        PostsInfo postsInfo = bizForumPostsMapper.selectPostsInfo(id);
        if(postsInfo == null){
            postsInfo = new PostsInfo();
        }else{
            //处理多张帖子图片
            List<ImgInfo> postImglist = new ArrayList<>();
            ImgInfo postImgInfo = new ImgInfo();
            String[] postImages = postsInfo.getPostImage().split(",");
            for (String url : postImages){
                postImgInfo.setUrl(url);
                postImglist.add(postImgInfo);
            }
            postsInfo.setPostImageList(postImglist);
        }
        result.add(postsInfo);
        return result;
    }

    /**
     * 根据项目id查询所关联的小组
     * @param projectId
     * @return
     */
    public List<GroupInfo> getGroupInfo(String projectId){
        //查询当前租户id的身份
        String tenantType = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(tenantType)){
            projectId = bizProductMapper.selectProjectById(BaseContextHandler.getTenantID());
        }
        List<GroupInfo> groupInfoList =  bizForumPostsMapper.selectGroupByProjectId(projectId);
        if(groupInfoList == null || groupInfoList.size() == 0){
            groupInfoList = new ArrayList<>();
        }
        return groupInfoList;
    }



    /**
     * 查询帖子评论列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    public List<CommentVo> getCommentList(String id, Integer page, Integer limit){
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
        List<CommentVo> commentVoList = bizCommentMapper.selectCommentList(id,startIndex,limit);
        if(commentVoList == null || commentVoList.size() == 0){
            commentVoList = new ArrayList<>();
        }
        return commentVoList;
    }

     public int selectCommentCount(String id){
        return bizCommentMapper.selectCommentCount(id);
     }

    /**
     *  评论操作显示,隐藏
     * @param param
     * @return
     */
    public ObjectRestResponse updateCommentStatus(UpdateCommentStatus param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(501);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(bizCommentMapper.updateCommentStatusById( BaseContextHandler.getUserID(),param.getStatus(),param.getId()) < 0){
            msg.setStatus(503);
            msg.setMessage("操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }







}