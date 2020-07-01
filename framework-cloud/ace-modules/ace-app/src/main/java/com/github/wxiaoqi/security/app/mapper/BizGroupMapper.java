package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizGroup;
import com.github.wxiaoqi.security.app.vo.group.out.*;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

/**
 * 热门小组表
 * 
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
public interface BizGroupMapper extends CommonMapper<BizGroup> {
    //获取小组分类下的小组列表
    String selectGroupClassify(@Param("id")String id);
    /**
     * 获取小组列表 APP
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultGroupListVo> selectAppGroupList(@Param("projectId") String projectId,@Param("searchVal") String searchVal);
    /**
     * 取消组长
     * @return
     */
    int updateGroupLeaderStatus(@Param("status")String status,@Param("userId")String userId,@Param("groupId")String groupId);

    /**
     * 获取小组详情 app
     * @param groupId
     * @return
     */
    ResultAppGroupInfoVo selectAppGroupInfo(@Param("groupId") String groupId);
    //获取小组排名
    List<ResultGroupRankVo> getGroupRank(@Param("projectId") String projectId);
    //获取成员数
    int getGroupMemberCount(@Param("groupId") String groupId);
    //获取打卡数
    int getGroupSignCount(@Param("groupId") String groupId);
    //获取组长数
    int getGroupLeaderCount(@Param("groupId") String groupId);

    //获取小组成员
    List<ResultAppMemberVo> selectGroupMemberInfo(@Param("groupId") String groupId);

    //获取小组成员打卡情况
    Date selectMemberSignInfo(@Param("userId") String userId,@Param("groupId")String groupId);

    //获取成员信息
    ResultMemberInfo selectMemberInfo(@Param("userId") String userId,@Param("groupId")String groupId);

    //获取我的小组列表
    List<ResultGroupListVo> getMyGroupList(@Param("projectId") String projectId, @Param("userId") String userId);
    //获取热门小组
    List<ResultGroupListVo> getHotGroupList(@Param("projectId") String projectId);
    //获取最新小组
    List<ResultGroupListVo> getNewGroupList(@Param("projectId") String projectId);


    /**
     * 查询项目下小组积分排名列表
     * @param projectId
     * @return
     */
    List<GroupIntegralVo> selectGroupIntegralList(@Param("projectId") String projectId,@Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 查询当前社区积分
     * @param projectId
     * @return
     */
    ProjectIntegralVo selectCurrentProjectIntegral(String projectId);

    /**
     * 查询社区积分排行榜
     * @param page
     * @param limit
     * @return
     */
    List<ProjectIntegralVo> selectAllProjectIntegral(@Param("page") Integer page,@Param("limit") Integer limit );
}
