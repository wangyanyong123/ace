package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizUserProject;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.userproject.out.OtherHousesAndProjectDetailInfosVo;
import com.github.wxiaoqi.security.app.vo.userproject.out.ProjectInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户和项目关系表
 * 
 * @author zxl
 * @Date 2018-11-22 15:22:31
 */
public interface BizUserProjectMapper extends CommonMapper<BizUserProject> {

	int setProjectIsNow(@Param("userId") String userId);

	int changOut(@Param("projectId")String oldProjectId, @Param("userId")String userID);

	int changNow(@Param("projectId")String newProjectId, @Param("userId")String userID);

	ProjectInfoVo getCurrentProjectInfoById(@Param("id")String id);

	List<ProjectInfoVo> getProjectListsByUserId(@Param("userId") String userId);

	BizUserProject getProjectByHouseIdAndUserId(@Param("houseId")String houseId, @Param("userId")String userId);

	int changOutByUserId(@Param("userId") String userId);

	CurrentUserInfosVo getCurrentUserInfos(@Param("userId") String userId);

	List<OtherHousesAndProjectDetailInfosVo> getOtherHousesAndProjectDetailInfos(@Param("userId") String userId);
}
