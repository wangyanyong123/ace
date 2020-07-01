package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.user.ServerUserInfo;
import com.github.wxiaoqi.security.app.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.app.vo.city.out.ProjectInfoVo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * app服务端用户表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
public interface BaseAppServerUserMapper extends CommonMapper<BaseAppServerUser> {
    /**
     * 获取服务用户拥有技能
     * @param userId
     * @return
     */
    List<String> selectAppUserSkills(String userId);

    /**
     * 获取服务用户信息
     * @param userIdList
     * @return
     */
    List<ServerUserInfo> selectAppUserList(@Param("userIdList")List<String> userIdList);


	String getProjectId(@Param("userId")String userId);

    List<ProjectInfoVo> getServerUserProject(@Param("userId") String userId,@Param("tenantId")String tenantId);

    UserVo getUserNameById(@Param("userId") String userId);



    BaseAppServerUser selectUserByPhone(String phone);

	int isHasSupervision(@Param("userId") String userId);


	String selectCompanyNameById(String userId);

    BaseAppServerUser getUserInfo(String userId);

    /**
     * 查询用户类型
     * @param userId
     * @return
     */
    String selectRoleTypeByUser(@Param("userId") String userId);
}
