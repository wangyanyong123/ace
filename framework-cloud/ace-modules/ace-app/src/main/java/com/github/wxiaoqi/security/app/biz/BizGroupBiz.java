package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.entity.BizGroup;
import com.github.wxiaoqi.security.app.entity.BizGroupMember;
import com.github.wxiaoqi.security.app.entity.BizGroupSignLog;
import com.github.wxiaoqi.security.app.mapper.BizGroupLeaderMapper;
import com.github.wxiaoqi.security.app.mapper.BizGroupMapper;
import com.github.wxiaoqi.security.app.mapper.BizGroupMemberMapper;
import com.github.wxiaoqi.security.app.mapper.BizGroupSignLogMapper;
import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import com.github.wxiaoqi.security.app.vo.group.in.FollowStatus;
import com.github.wxiaoqi.security.app.vo.group.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.RuleThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private BizGroupMemberMapper bizGroupMemberMapper;
    @Autowired
    private BizGroupLeaderMapper bizGroupLeaderMapper;
    @Autowired
    private BizGroupSignLogMapper bizGroupSignLogMapper;
    @Autowired
    private BizIntegralPersonalDetailBiz bizIntegralPersonalDetailBiz;

    /**
     * 用户加入小组
     * @return
     */
    public ObjectRestResponse saveGroupMember(FollowStatus param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isAnyoneEmpty(param.getGroupId(),param.getFollowStatus())) {
            msg.setMessage("参数不能为空！");
            return msg;
        }
        BizGroupMember bizGroupMember = new BizGroupMember();
        ResultMemberInfo resultMemberInfo = bizGroupMapper.selectMemberInfo(BaseContextHandler.getUserID(),param.getGroupId());
        if (resultMemberInfo != null) {
            if (resultMemberInfo.getGroupId().equals(param.getGroupId())) {
                if (resultMemberInfo.getFollowStatus().equals(param.getFollowStatus())) {
                    msg.setStatus(101);
                    msg.setMessage("已加入小组");
                    return msg;
                }
                BeanUtils.copyProperties(resultMemberInfo,bizGroupMember);
                bizGroupMember.setFollowStatus(param.getFollowStatus());
                bizGroupMember.setTimeStamp(new Date());
                bizGroupMember.setModifyBy(BaseContextHandler.getUserID());
                bizGroupMember.setModifyTime(new Date());
                if (bizGroupMemberMapper.updateByPrimaryKeySelective(bizGroupMember) < 0) {
                    logger.error("更新数据失败,groupId{}",param.getGroupId());
                    msg.setStatus(102);
                    if (param.getFollowStatus().equals("2")) {
                        msg.setMessage("取消失败");
                    }else {
                        msg.setMessage("加入失败");
                    }
                    return msg;
                }
                if (param.getFollowStatus().equals("2")) {
                    bizGroupMapper.updateGroupLeaderStatus(param.getFollowStatus(),BaseContextHandler.getUserID(),param.getGroupId());
                    msg.setMessage("取消成功");
                    return msg;
                }
            }
        } else {
            //首次加入
            String memberId = UUIDUtils.generateUuid();
            bizGroupMember.setId(memberId);
            bizGroupMember.setGroupId(param.getGroupId());
            bizGroupMember.setUserId(BaseContextHandler.getUserID());
            bizGroupMember.setTimeStamp(new Date());
            bizGroupMember.setFollowStatus(param.getFollowStatus());
            bizGroupMember.setMemberStatus("1");
            bizGroupMember.setCreateBy(BaseContextHandler.getUserID());
            bizGroupMember.setCreateTime(new Date());
            msg.setData(memberId);
            if (bizGroupMemberMapper.insertSelective(bizGroupMember) < 0) {
                logger.error("保存数据失败,groupId{}",param.getGroupId());
                msg.setStatus(102);
                msg.setMessage("加入失败");
                return msg;
            }
            //个人首次加入兴趣小组,+10
            msg = bizIntegralPersonalDetailBiz.addPublicIntegarl(RuleThemeConstants.FIRST_ADD_GROUP,"","");
            if(msg.getStatus() != 102){
                msg.setStatus(200);
            }
            msg.setMessage("加入成功");

        }
        return msg;
    }

    /**
     * 获取小组列表  APP
     * @param projectId
     * @param searchVal
     * @return
     */
    public ObjectRestResponse getAppGroupList(String projectId, String searchVal) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(projectId)) {
            msg.setMessage("projectId不能为空！");
            return msg;
        }


        List<ResultAppGroupListVo> group = new ArrayList<>();
        List<ResultGroupListVo> groupListVos = bizGroupMapper.selectAppGroupList(projectId, searchVal);
        Map<String,List<ResultGroupListVo>> gropMap = groupListVos.stream().collect(Collectors.groupingBy(ResultGroupListVo::getClassifyName));
        gropMap.keySet().forEach(key -> {
            ResultAppGroupListVo appGroupListVo = new ResultAppGroupListVo();
            appGroupListVo.setClassifyName(key);
            appGroupListVo.setGroupListVo(gropMap.get(key));
            group.add(appGroupListVo);
        });
        msg.setData(group);
        return msg;
    }

    /**
     * 获取我的小组
     * @param projectId
     * @return
     */
    public ObjectRestResponse getMyGroupList(String projectId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(projectId)) {
            msg.setMessage("projectId不能为空！");
            return msg;
        }
        List<ResultGroupListVo> myGroupList = bizGroupMapper.getMyGroupList(projectId, BaseContextHandler.getUserID());
        if (myGroupList == null || myGroupList.size() == 0) {
            myGroupList = new ArrayList<>();
            msg.setMessage("该用户没有小组");
        }
        for (ResultGroupListVo myGroup : myGroupList) {
            List<ImgeInfo> list = new ArrayList<>();
            ImgeInfo imgeInfo = new ImgeInfo();
            if (!myGroup.getLogoImage().equals("")) {
                imgeInfo.setUrl(myGroup.getLogoImage());
            }else {
                imgeInfo.setUrl("");
            }
            list.add(imgeInfo);
            myGroup.setLogo(list);
        }
        msg.setData(myGroupList);
        return msg;
    }
    /**
     * 获取热门小组
     * @param projectId
     * @return
     */
    public ObjectRestResponse getHotGroupList(String projectId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(projectId)) {
            msg.setMessage("projectId不能为空！");
            return msg;
        }
        List<ResultGroupListVo> hotGroupList = bizGroupMapper.getHotGroupList(projectId);
        if (hotGroupList == null || hotGroupList.size() == 0) {
            hotGroupList = new ArrayList<>();
            msg.setMessage("暂无热门小组");
        }
        for (ResultGroupListVo hotGroup : hotGroupList) {
            List<ImgeInfo> list = new ArrayList<>();
            ImgeInfo imgeInfo = new ImgeInfo();
            if (!hotGroup.getLogoImage().equals("")) {
                imgeInfo.setUrl(hotGroup.getLogoImage());
            }else {
                imgeInfo.setUrl("");
            }
            list.add(imgeInfo);
            hotGroup.setLogo(list);
        }

        msg.setData(hotGroupList);
        return msg;
    }
    /**
     * 获取最新小组
     * @param projectId
     * @return
     */
    public ObjectRestResponse getNewGroupList(String projectId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(projectId)) {
            msg.setMessage("projectId不能为空！");
            return msg;
        }
        List<ResultGroupListVo> newGroupList = bizGroupMapper.getNewGroupList(projectId);
        if (newGroupList == null || newGroupList.size() == 0) {
            newGroupList = new ArrayList<>();
            msg.setMessage("暂无最新小组");
        }
        for (ResultGroupListVo newGroup : newGroupList) {
            List<ImgeInfo> list = new ArrayList<>();
            ImgeInfo imgeInfo = new ImgeInfo();
            if (!newGroup.getLogoImage().equals("")) {
                imgeInfo.setUrl(newGroup.getLogoImage());
            }else {
                imgeInfo.setUrl("");
            }
            list.add(imgeInfo);
            newGroup.setLogo(list);
        }
        msg.setData(newGroupList);
        return msg;
    }

    /**
     * 获取小组详情 app
     * @param id
     * @return
     */
    public ObjectRestResponse getAppGroupInfo(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            msg.setMessage("ID不能为空");
            return msg;
        }
        ResultAppGroupInfoVo appGroupInfoVo = bizGroupMapper.selectAppGroupInfo(id);
        ResultMemberInfo resultMemberInfo = bizGroupMapper.selectMemberInfo(BaseContextHandler.getUserID(), id);
        Date date = bizGroupMapper.selectMemberSignInfo(BaseContextHandler.getUserID(), id);
        BizGroup bizGroup = bizGroupMapper.selectByPrimaryKey(id);
        List<ResultGroupRankVo> groupRank = bizGroupMapper.getGroupRank(bizGroup.getProjectId());
        if (date != null) {
            //已打卡
            appGroupInfoVo.setSignStatus("1");
        }else {
            appGroupInfoVo.setSignStatus("0");
        }
        if (resultMemberInfo != null) {
            //已是小组成员
            appGroupInfoVo.setStatus("1");
        }else {
            appGroupInfoVo.setStatus("0");
        }
        if (groupRank != null) {
            for (int i = 0; i < groupRank.size(); i++) {
                if (groupRank.get(i).getId().equals(appGroupInfoVo.getGroupId())) {
                    appGroupInfoVo.setRank(i+1);
                    appGroupInfoVo.setCreditsValue(groupRank.get(i).getCreditsValue());
                }
            }
        }else {
            appGroupInfoVo.setCreditsValue("");
        }
        if (appGroupInfoVo!=null) {
            int memberCount = bizGroupMapper.getGroupMemberCount(id);
            int signCount = bizGroupMapper.getGroupSignCount(id);
            int leaderCount = bizGroupMapper.getGroupLeaderCount(id);
            if (memberCount > 0) {
                appGroupInfoVo.setMemberCount(memberCount);
            }else {
                appGroupInfoVo.setMemberCount(0);
            }
            if (signCount > 0) {
                appGroupInfoVo.setSignCount(signCount);
            }else {
                appGroupInfoVo.setSignCount(0);
            }
            if (leaderCount > 0) {
                appGroupInfoVo.setLeaderCount(leaderCount);
            }else {
                appGroupInfoVo.setLeaderCount(0);
            }
            if (appGroupInfoVo.getLogoImage()!=null) {
                    List<ImgeInfo> list = new ArrayList<>();
                    ImgeInfo imgeInfo = new ImgeInfo();
                    imgeInfo.setUrl(appGroupInfoVo.getLogoImage());
                    list.add(imgeInfo);
                    appGroupInfoVo.setLogo(list);
            }else{
                appGroupInfoVo.setLogo(new ArrayList<>());
            }
            //获取组长
            List<ResultLeaderInfoVo> resultLeaderInfoVo = bizGroupLeaderMapper.selectGroupLeaderList(id);
            if (resultLeaderInfoVo.size() > 0 && resultLeaderInfoVo != null) {
                for (ResultLeaderInfoVo leaderInfoVo : resultLeaderInfoVo) {
                    List<ImgeInfo> list = new ArrayList<>();
                    ImgeInfo imgeInfo = new ImgeInfo();
                    imgeInfo.setUrl(leaderInfoVo.getLeaderPhoto());
                    list.add(imgeInfo);
                    leaderInfoVo.setPhoto(list);
                }
                appGroupInfoVo.setLeader(resultLeaderInfoVo);
            }else {
                appGroupInfoVo.setLeader(new ArrayList<>());
            }
        }else {
            ResultAppGroupInfoVo groupInfoVo = new ResultAppGroupInfoVo();
            groupInfoVo.setGroupId("");
            groupInfoVo.setName("");
            groupInfoVo.setLogo(new ArrayList<>());
            groupInfoVo.setLogoImage("");
            groupInfoVo.setGroupId("");
            groupInfoVo.setMemberCount(0);
            groupInfoVo.setLeaderCount(0);
            groupInfoVo.setSignCount(0);
            groupInfoVo.setLeader(new ArrayList<>());
            groupInfoVo.setSummary("");
        }
        msg.setData(appGroupInfoVo);
        return msg;
    }

    /**
     * 获取成员
     * @param groupId
     * @return
     */
    public ObjectRestResponse getAppGroupMemberInfo(String groupId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(groupId)) {
            msg.setMessage("小组ID不能为空");
            msg.setStatus(102);
            return msg;
        }
        List<ResultAppMemberVo> resultAppMemberVos = bizGroupMapper.selectGroupMemberInfo(groupId);
        if (resultAppMemberVos.size()>0 && resultAppMemberVos!=null) {
            for (ResultAppMemberVo memberVo : resultAppMemberVos) {
                if (memberVo.getUserPhoto() != null) {
                    List<ImgeInfo> list = new ArrayList<>();
                    ImgeInfo imgeInfo = new ImgeInfo();
                    imgeInfo.setUrl(memberVo.getUserPhoto());
                    list.add(imgeInfo);
                    memberVo.setPhoto(list);
                }else{
                    memberVo.setPhoto(new ArrayList<>());
                }
            }
        }
        msg.setData(resultAppMemberVos);
        return msg;
    }

    /**
     * 成员打卡
     * @return
     */
    public ObjectRestResponse saveGroupMemberSign(String groupId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(groupId)) {
            msg.setMessage("小组ID不能为空");
            msg.setStatus(102);
            return msg;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BizGroupSignLog bizGroupSignLog = new BizGroupSignLog();
        ResultMemberInfo resultMemberInfo = bizGroupMapper.selectMemberInfo(BaseContextHandler.getUserID(), groupId);
        if (resultMemberInfo == null) {
            msg.setMessage("打卡失败，该用户尚未加入小组");
            msg.setStatus(102);
            return msg;
        }
        java.sql.Date signDate = bizGroupMapper.selectMemberSignInfo(BaseContextHandler.getUserID(),groupId);
        try {
            if (signDate != null) {
                System.out.println(sdf.format(new Date()));
                if (sdf.format(signDate).equals(sdf.format(new Date())) ) {
                    msg.setMessage("已打卡,不能重复打卡");
                    msg.setStatus(102);
                    return msg;
                }
            }else {
                String signId = UUIDUtils.generateUuid();
                bizGroupSignLog.setId(signId);
                bizGroupSignLog.setGroupId(groupId);
                bizGroupSignLog.setUserId(BaseContextHandler.getUserID());
                System.out.println(sdf.parse(sdf.format(new Date())));
                bizGroupSignLog.setSignDate(sdf.parse(sdf.format(new Date())));
                bizGroupSignLog.setCreateBy(BaseContextHandler.getUserID());
                bizGroupSignLog.setCreateTime(new Date());
                bizGroupSignLog.setTimeStamp(new Date());
                msg.setData(signId);
                if (bizGroupSignLogMapper.insertSelective(bizGroupSignLog) < 0) {
                    logger.error("打卡失败,groupId{}",groupId);
                    msg.setStatus(102);
                    msg.setMessage("打卡失败");
                    return msg;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //组员打卡成功,+10
        msg = bizIntegralPersonalDetailBiz.addPublicIntegarl(RuleThemeConstants.GROUP_PUNCH_CARD,groupId,"");
        //个人打卡 +3
        msg = bizIntegralPersonalDetailBiz.addPublicIntegarl(RuleThemeConstants.PUNCH_CARD,"","");
        msg.setMessage("打卡成功");
        if(msg.getStatus() != 102){
            msg.setStatus(200);
        }
        return msg;
    }


    /**
     * 查询项目下小组积分排名列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<GroupIntegralVo>> getGroupIntegralList(String projectId, Integer page, Integer limit){
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
        List<GroupIntegralVo> groupIntegralVoList = bizGroupMapper.selectGroupIntegralList(projectId, startIndex, limit);
        for(GroupIntegralVo integralVo: groupIntegralVoList){
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(integralVo.getLogoImage());
            list.add(imgInfo);
            integralVo.setLogoImageList(list);
        }
        if(groupIntegralVoList == null || groupIntegralVoList.size() == 0){
            groupIntegralVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(groupIntegralVoList);
    }


    /**
     * 查询当前社区积分
     * @param projectId
     * @return
     */
    public ObjectRestResponse<ProjectIntegralVo> getCurrentProjectIntegral(String projectId){
        ProjectIntegralVo projectIntegralInfo = bizGroupMapper.selectCurrentProjectIntegral(projectId);
        if(projectIntegralInfo == null){
            projectIntegralInfo = new ProjectIntegralVo();
        }
        return ObjectRestResponse.ok(projectIntegralInfo);
    }


    /**
     * 查询社区积分排行榜
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<ProjectIntegralVo>> getAllProjectIntegral(Integer page, Integer limit){
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
        List<ProjectIntegralVo> projectIntegralVoList = bizGroupMapper.selectAllProjectIntegral(startIndex,limit);
        if(projectIntegralVoList == null || projectIntegralVoList.size() == 0){
            projectIntegralVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(projectIntegralVoList);
    }


}