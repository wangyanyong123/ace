package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizGroup;
import com.github.wxiaoqi.security.jinmao.vo.group.in.GroupStatus;
import com.github.wxiaoqi.security.jinmao.vo.group.out.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 热门小组表
 * 
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
public interface BizGroupMapper extends CommonMapper<BizGroup> {

    /**
     * 查询小组列表
     * @return
     */
    List<ResultGroupListVo> selectGroupList(@Param("projectId")List<String> projectId,@Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal,
                                            @Param("page") Integer page, @Param("limit") Integer limit);

    String getGroupName(@Param("name") String name, @Param("projectId") String projectId);

    /**
     * 查询小组数量
     *
	 * @param enableStatus
	 * @param searchVal
	 * @return
     */
    int selectGroupCount(@Param("ids")List<String> ids, @Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal);

    /**
     * 查询小组详情
     * @param id
     * @return
     */
    ResultGroupInfoVo selectGroupInfoById(@Param("id") String id);

    /**
     * 查询小组分类
     * @param classifyCode
     * @return
     */
    List<ResultClassifyInfoVo> selectGroupClassify(@Param("classifyCode") String classifyCode);

    /**
     * 修改小组状态
     * @param param
     * @return
     */
    int updateGroupStatus(GroupStatus param);

    /**
     * 加入/取消组长
     * @return
     */
    int updateGroupLeaderStatus(@Param("status")String status,@Param("userId")String userId,@Param("groupId")String groupId);

    /**
     * 修改成员性质
     * @return
     */
    int updateGroupMemberStatus(@Param("status")String status,@Param("userId")String userId,@Param("groupId")String groupId);

    /**
     * 删除小组
     * @param id
     * @return
     */
    int deleteGroupById(@Param("id") String id);

    /**
     * 查询小组项目
     * @param id
     * @return
     */
    ResultProject selectGroupProjectInfo(@Param("id") String id);

    /**
     * 查询小组分类
     * @param id
     * @return
     */
    ResultClassify selectGroupClassifyInfo(@Param("id") String id);

}
