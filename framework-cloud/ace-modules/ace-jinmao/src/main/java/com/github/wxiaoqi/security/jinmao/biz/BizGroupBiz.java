package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizGroup;
import com.github.wxiaoqi.security.jinmao.entity.BizGroupLeader;
import com.github.wxiaoqi.security.jinmao.feign.AdminFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BizGroupLeaderMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizGroupMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizGroupMemberMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.group.in.GroupMemberStatus;
import com.github.wxiaoqi.security.jinmao.vo.group.in.GroupStatus;
import com.github.wxiaoqi.security.jinmao.vo.group.in.SaveGroupVo;
import com.github.wxiaoqi.security.jinmao.vo.group.out.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 热门小组表
 *
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
@Service
public class BizGroupBiz extends BusinessBiz<BizGroupMapper,BizGroup> {

    private Logger logger = LoggerFactory.getLogger(BizGroupBiz.class);
    @Autowired
    private BizGroupMapper bizGroupMapper;
    @Autowired
    private BizGroupLeaderMapper bizGroupLeaderMapper;
    @Autowired
    private BizGroupMemberMapper bizGroupMemberMapper;
    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private ToolFegin toolFeign;

    /**
     * 查询小组列表
     * @param enablestatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultGroupListVo> selectGroupList(List<String> projectId,String enablestatus, String searchVal, Integer page, Integer limit) {
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
        List<ResultGroupListVo> groupListVos = bizGroupMapper.selectGroupList(projectId,enablestatus, searchVal, startIndex, limit);
        for (ResultGroupListVo groupListVo : groupListVos) {
            List<ResultLeaderInfoVo> resultLeaderInfoVo = bizGroupLeaderMapper.selectGroupLeaderList(groupListVo.getId());
            if (resultLeaderInfoVo.size() > 0 && resultLeaderInfoVo != null) {
                String leaderName = "";
                StringBuilder sb = new StringBuilder();
                for (ResultLeaderInfoVo leaderInfoVo : resultLeaderInfoVo) {
                    sb.append(leaderInfoVo.getLeaderName() + ",");
                }
                leaderName = sb.substring(0, sb.length() - 1);
                groupListVo.setLeader(leaderName);
            }else {
                groupListVo.setLeader("");
            }
        }
        return groupListVos;
    }

    /**
     * 查询小组数量
     *
     * @param id
     * @param enableStatus
     * @param searchVal
     * @return
     */
    public int selectGroupCount(List<String> id, String enableStatus, String searchVal) {
        int total = bizGroupMapper.selectGroupCount(id,enableStatus, searchVal);
        return total;
    }

    /**
     * 根据ID查询小组详情
     * @param id
     * @return
     */
    public List<ResultGroupInfoVo> selectGroupInfoById(String id) {
        List<ResultGroupInfoVo> groupInfo = new ArrayList<>();
        ResultGroupInfoVo resultGroupInfoVo = bizGroupMapper.selectGroupInfoById(id);
        ResultProject resultProject = bizGroupMapper.selectGroupProjectInfo(id);
        ResultClassify resultClassify = bizGroupMapper.selectGroupClassifyInfo(id);
        if (resultGroupInfoVo != null) {
            if (resultGroupInfoVo.getLogoImage()!=null) {
                List<ImgInfo> logoImages = new ArrayList<>();
                ImgInfo logoImage = new ImgInfo();
                logoImage.setUrl(resultGroupInfoVo.getLogoImage());
                logoImages.add(logoImage);
                resultGroupInfoVo.setLogo(logoImages);
                if (resultProject != null && resultClassify != null) {
                    List<ResultProject> projectList = new ArrayList<>();
                    List<ResultClassify> classifyList = new ArrayList<>();
                    projectList.add(resultProject);
                    classifyList.add(resultClassify);
                    resultGroupInfoVo.setProjectInfo(projectList);
                    resultGroupInfoVo.setClassifyInfo(classifyList);
                }else {
                    resultGroupInfoVo.setProjectInfo(new ArrayList<>());
                    resultGroupInfoVo.setClassifyInfo(new ArrayList<>());
                }

            }
            groupInfo.add(resultGroupInfoVo);
        }
        return groupInfo;
    }

    /**
     * 修改小组状态
     * @param param
     * @return
     */
    public ObjectRestResponse updateGroupStatus(GroupStatus param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizGroupMapper.updateGroupStatus(param) < 0) {
            logger.error("修改状态失败，id为{}", param.getId());
            msg.setStatus(102);
            msg.setMessage("修改失败！");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询小组分类
     * @param classifyCode
     * @return
     */
    public List<ResultClassifyInfoVo> selectGroupClassify(String classifyCode) {
        return bizGroupMapper.selectGroupClassify(classifyCode);
    }

    /**
     * 加入/取消组长
     * @param param
     * @return
     */
    public ObjectRestResponse updateGroupLeaderStatus(GroupMemberStatus param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (param.getStatus().equals("2")) {
            BizGroupLeader bizGroupLeader = new BizGroupLeader();
            bizGroupLeader.setId(UUIDUtils.generateUuid());
            bizGroupLeader.setGroupId(param.getGroupId());
            bizGroupLeader.setUserId(param.getUserId());
            bizGroupLeader.setCreateBy(BaseContextHandler.getUserID());
            bizGroupLeader.setCreateTime(new Date());
            bizGroupLeader.setTimeStamp(new Date());
            if (bizGroupLeaderMapper.insertSelective(bizGroupLeader) > 0) {
                if (bizGroupMapper.updateGroupMemberStatus(param.getStatus(),param.getUserId(),param.getGroupId())<0) {
                    logger.error("修改状态失败，userId为{}", param.getUserId());
                    msg.setStatus(102);
                    msg.setMessage("修改失败！");
                    return msg;
                }
            }
        }else {
            if (bizGroupMapper.updateGroupLeaderStatus(param.getStatus(), param.getUserId(),param.getGroupId()) > 0) {
                if (bizGroupMapper.updateGroupMemberStatus(param.getStatus(),param.getUserId(),param.getGroupId())<0) {
                    logger.error("修改状态失败，userId为{}", param.getUserId());
                    msg.setStatus(102);
                    msg.setMessage("修改失败！");
                    return msg;
                }
            }else {
                msg.setMessage("该用户不是组长");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 获取小组成员信息
     * @param groupId
     * @return
     */
    public List<ResultGroupMemberInfoVo> getGroupMemberInfoList(String groupId,String searchVal,Integer page,Integer limit) {
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
        List<ResultGroupMemberInfoVo> groupMemberInfoVo = new ArrayList<>();
        ResultGroupMemberInfoVo memberInfoVo = new ResultGroupMemberInfoVo();
        List<ResultGroupMemberVo> resultMemberInfoVo = bizGroupMemberMapper.selectGroupMemberList(groupId,searchVal,startIndex,limit);
        //获取小组组长
        List<ResultLeaderInfoVo> resultLeaderInfoVo = bizGroupLeaderMapper.selectGroupLeaderList(groupId);
            if (resultLeaderInfoVo != null && resultLeaderInfoVo.size()> 0) {
                List<ResultLeaderInfoVo> leaderList = new ArrayList<>();
                for (ResultLeaderInfoVo leaderInfoVo : resultLeaderInfoVo) {
                    leaderList.add(leaderInfoVo);
                }
                memberInfoVo.setMember(resultMemberInfoVo);
                memberInfoVo.setLeader(leaderList);
            }else {
                resultLeaderInfoVo = new ArrayList<>();
                memberInfoVo.setMember(resultMemberInfoVo);
                memberInfoVo.setLeader(resultLeaderInfoVo);
            }

        groupMemberInfoVo.add(memberInfoVo);
        return groupMemberInfoVo;
    }

    /**
     * 查询小组数量
     * @param groupId
     * @param searchVal
     * @return
     */
    public int getGroupMemberCount(String groupId, String searchVal) {
        int total = bizGroupMemberMapper.selectGroupMemberCount(groupId, searchVal);
        return total;
    }

    /**
     * 保存小组信息
     * @param param
     * @return
     */
    public ObjectRestResponse<SaveGroupVo> saveGroupInfo(SaveGroupVo param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        for (Map<String, String> map : param.getProjectInfo()) {
            String groupName = bizGroupMapper.getGroupName(param.getName(), map.get("projectId"));
            if (groupName != null) {
                msg.setStatus(101);
                msg.setMessage("该小组名字已经存在！");
                return msg;
            }
        }
        BizGroup bizGroup = new BizGroup();
        String groupId = UUIDUtils.generateUuid();
        bizGroup.setId(groupId);
        bizGroup.setName(param.getName());
        if (param.getLogo() != null && param.getLogo().size() > 0) {
            for (ImgInfo imgInfo : param.getLogo()) {
                if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                    ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.OWNERCIRCLE);
                    if(objectRestResponse.getStatus()==200){
                        bizGroup.setLogoImage(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                    }
                }
                //bizGroup.setLogoImage(imgInfo.getUrl());
            }
        }else {
            bizGroup.setLogoImage("");
        }
        bizGroup.setGrade("1");
        bizGroup.setSummary(param.getSummary());
        if (param.getClassifyInfo() != null) {
            for (Map<String, String> projectInfo : param.getProjectInfo()) {
                bizGroup.setProjectId(projectInfo.get("projectId"));
            }
        }
        if (param.getClassifyInfo() != null) {
            for (Map<String, String> classifyInfo : param.getClassifyInfo()) {
                bizGroup.setClassifyCode(classifyInfo.get("code"));
                Map<String, String> map =adminFeign.getDictValue("groupClassify");
               /* List<ResultClassifyInfoVo> classifys = bizGroupMapper.selectGroupClassify(classifyInfo.get("code"));
                for (ResultClassifyInfoVo classify: classifys) {
                    bizGroup.setClassifyName(classify.getClassifyName());
                }*/
                bizGroup.setClassifyName(map.get(classifyInfo.get("code")));
            }
        }
        bizGroup.setCreateBy(BaseContextHandler.getUserID());
        bizGroup.setCreateTime(new Date());
        bizGroup.setTimeStamp(new Date());
        msg.setData(groupId);
        if (bizGroupMapper.insertSelective(bizGroup) < 0) {
            logger.error("保存小组设置失败,groupId为{}",groupId);
            msg.setStatus(101);
            msg.setMessage("保存设置失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 编辑小组信息
     * @param param
     * @return
     */
    public ObjectRestResponse updateGroupInfo(SaveGroupVo param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultGroupInfoVo resultGroupInfoVo = bizGroupMapper.selectGroupInfoById(param.getId());
        ResultProject resultProject = bizGroupMapper.selectGroupProjectInfo(param.getId());
        for (Map<String, String> map : param.getProjectInfo()) {
            String groupName = bizGroupMapper.getGroupName(param.getName(), map.get("projectId"));
            if (!resultProject.getProjectName().equals(map.get("projectName"))) {
                if (groupName!=null) {
                    msg.setStatus(101);
                    msg.setMessage("该小组名字已经存在！");
                    return msg;
                }
            }
        }
        BizGroup bizGroup = new BizGroup();
        if (resultGroupInfoVo != null) {
            BeanUtils.copyProperties(resultGroupInfoVo,bizGroup);
            bizGroup.setName(param.getName());
            if (param.getLogo() != null && param.getLogo().size() > 0) {
                for (ImgInfo imgInfo : param.getLogo()) {
                    if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                        ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.OWNERCIRCLE);
                        if(objectRestResponse.getStatus()==200){
                            bizGroup.setLogoImage(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                        }
                    }
                    //bizGroup.setLogoImage(imgInfo.getUrl());
                }
            }else {
                bizGroup.setLogoImage("");
            }
            bizGroup.setGrade("1");
            bizGroup.setSummary(param.getSummary());
            for (Map<String, String> projectInfo : param.getProjectInfo()) {
                bizGroup.setProjectId(projectInfo.get("projectId"));
            }
            for (Map<String, String> classifyInfo : param.getClassifyInfo()) {
                bizGroup.setClassifyCode(classifyInfo.get("code"));
                Map<String, String> map =adminFeign.getDictValue("groupClassify");
                bizGroup.setClassifyName(map.get(classifyInfo.get("code")));
            }
            bizGroup.setModifyBy(BaseContextHandler.getUserID());
            bizGroup.setModifyTime(new Date());
            if (bizGroupMapper.updateByPrimaryKeySelective(bizGroup) < 0) {
                logger.error("编辑小组设置失败,groupId为{}",param.getId());
                msg.setStatus(101);
                msg.setMessage("更新小组设置失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 删除小组
     * @param id
     * @return
     */
    public ObjectRestResponse deleteGroupById(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizGroupMapper.deleteGroupById(id)<0 || bizGroupMapper.deleteGroupById(id)==0) {
            logger.error("删除小组设置失败,id为{}",id);
            msg.setStatus(101);
            msg.setMessage("删除小组设置失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

}