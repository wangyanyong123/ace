package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizCommunityTopic;
import com.github.wxiaoqi.security.jinmao.entity.BizCommunityTopicProject;
import com.github.wxiaoqi.security.jinmao.mapper.BizCommunityTopicMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizCommunityTopicProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.CommunityTopicParam;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.CommentVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private BizCommunityTopicProjectMapper bizCommunityTopicProjectMapper;


    /**
     * 查询社区话题列表
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
    public List<CommunityTopicVo> getCommunityTopicList(String projectId,String showType,String isTop,String startTime,String endTime,
                                               String searchVal,Integer page, Integer limit){
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
//        List<CommunityTopicVo> topicVoList = bizCommunityTopicMapper.selectCommunityTopicList(projectId, showType, isTop,
//                startTime, endTime, searchVal, startIndex, limit);
        List<CommunityTopicVo> topicVoList = bizCommunityTopicMapper.selectNewCommunityTopicList(projectId, showType, isTop,
                startTime, endTime, searchVal, startIndex, limit);
        if(topicVoList != null && topicVoList.size() > 0){
            for (CommunityTopicVo topicVo: topicVoList){
               String userName = bizCommunityTopicMapper.selectUserNameByUserId(topicVo.getUserId());
               if(userName != null && !StringUtils.isEmpty(userName)){
                   topicVo.setUserName(userName);
               }
               List<String> projectNameList =  bizCommunityTopicProjectMapper.selectProjectNameById(topicVo.getId());
                String projectNames = "";
                StringBuilder sb = new StringBuilder();
                if(projectNameList != null && projectNameList.size() >0){
                    for (String projectName : projectNameList){
                        sb.append(projectName+",");
                    }
                }
                if(sb.toString() != null && sb.length() > 0){
                    projectNames = sb.substring(0,sb.length()-1);
                }
                topicVo.setProjectNames(projectNames);
            }
        }
        return topicVoList;
    }

    /**
     * 根据话题id查询所属项目
     * @param projectId
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    public int selectCommunityTopicCount(String projectId,String showType,String isTop,String startTime,String endTime,
                                         String searchVal){
        return bizCommunityTopicMapper.selectCommunityTopicCount(projectId, showType, isTop, startTime, endTime, searchVal);
    }


    /**
     * 保存社区话题
     * @param param
     * @return
     */
    public ObjectRestResponse saveCommunityTopic(CommunityTopicParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getTitle())){
            msg.setStatus(1002);
            msg.setMessage("话题标题不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getContent())){
            msg.setStatus(1002);
            msg.setMessage("话题内容不能为空");
            return msg;
        }
        if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        StringBuilder sb = new StringBuilder();
        BizCommunityTopic communityTopic = new BizCommunityTopic();
        String id = UUIDUtils.generateUuid();
        communityTopic.setId(id);
        communityTopic.setTitle(param.getTitle());
        communityTopic.setContent(param.getContent());
        communityTopic.setUserId(BaseContextHandler.getUserID());
        String postImages = "";
        if(param.getPostImageList() != null && param.getPostImageList().size() > 0){
            for(ImgInfo temp: param.getPostImageList()){
                sb.append(temp.getUrl()+",");
            }
            if(sb.toString()!= null && sb.length()>0){
                postImages = sb.substring(0,sb.length()-1);
            }
            communityTopic.setPostImage(postImages);
        }
        communityTopic.setCreateBy(BaseContextHandler.getUserID());
        communityTopic.setTimeStamp(new Date());
        communityTopic.setCreateTime(new Date());
        if(bizCommunityTopicMapper.insertSelective(communityTopic) > 0){
            //保存项目
            for (ResultProjectVo temp: param.getProjectVo()){
                BizCommunityTopicProject project = new BizCommunityTopicProject();
                project.setId(UUIDUtils.generateUuid());
                project.setProjectId(temp.getId());
                project.setCommunityTopicId(id);
                project.setCreateBy(BaseContextHandler.getUserID());
                project.setCreateDate(new Date());
                project.setStatus("1");
                bizCommunityTopicProjectMapper.insertSelective(project);
            }
        }else{
            msg.setStatus(105);
            msg.setMessage("保存社区话题失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询社区话题详情
     * @param id
     * @return
     */
    public ObjectRestResponse<CommunityTopicInfo> getCommunityTopicInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        CommunityTopicInfo communityTopicInfo = bizCommunityTopicMapper.selectCommunityTopicInfo(id);
        if(communityTopicInfo != null){
           List<ResultProjectVo> projectVoList = bizCommunityTopicProjectMapper.selectProjectInfoById(id);
            communityTopicInfo.setProjectVo(projectVoList);
        }
        return ObjectRestResponse.ok(communityTopicInfo);
    }


    /**
     * 编辑社区话题
     * @param param
     * @return
     */
    public ObjectRestResponse updateCommunityTopic(CommunityTopicParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getTitle())){
            msg.setStatus(1002);
            msg.setMessage("话题标题不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getContent())){
            msg.setStatus(1002);
            msg.setMessage("话题内容不能为空");
            return msg;
        }
        if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        StringBuilder sb = new StringBuilder();
        CommunityTopicInfo communityTopicInfo = bizCommunityTopicMapper.selectCommunityTopicInfo(param.getId());
        if(communityTopicInfo != null){
            BizCommunityTopic communityTopic = new BizCommunityTopic();
            BeanUtils.copyProperties(communityTopicInfo, communityTopic);
            communityTopic.setTitle(param.getTitle());
            communityTopic.setContent(param.getContent());
            String postImages = "";
            if(param.getPostImageList() != null && param.getPostImageList().size() > 0){
                for(ImgInfo temp: param.getPostImageList()){
                    sb.append(temp.getUrl()+",");
                }
                if(sb.toString()!= null && sb.length()>0){
                    postImages = sb.substring(0,sb.length()-1);
                }
                communityTopic.setPostImage(postImages);
            }
            communityTopic.setModifyBy(BaseContextHandler.getUserID());
            communityTopic.setModifyTime(new Date());
            if(bizCommunityTopicMapper.updateByPrimaryKeySelective(communityTopic) > 0){
                if(bizCommunityTopicProjectMapper.deleteCommunityTopicById(param.getId()) > 0){
                    //保存项目
                    for (ResultProjectVo temp: param.getProjectVo()){
                        BizCommunityTopicProject project = new BizCommunityTopicProject();
                        project.setId(UUIDUtils.generateUuid());
                        project.setProjectId(temp.getId());
                        project.setCommunityTopicId(param.getId());
                        project.setCreateBy(BaseContextHandler.getUserID());
                        project.setCreateDate(new Date());
                        project.setStatus("1");
                        bizCommunityTopicProjectMapper.insertSelective(project);
                    }
                }
            }else{
                msg.setStatus(1101);
                msg.setMessage("编辑社区话题失败!");
                return msg;
            }
        }else{
            msg.setStatus(1101);
            msg.setMessage("查无此详情!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
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
     * 查询评论列表
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
        List<CommentVo> commentVoList = bizCommunityTopicMapper.selectComunityTopicCommentList(id,startIndex,limit);
        if(commentVoList == null || commentVoList.size() == 0){
            commentVoList = new ArrayList<>();
        }
        return commentVoList;
    }

    public int selectCommentCount(String id){
        return bizCommunityTopicMapper.selectComunityTopicCommentCount(id);
    }









}