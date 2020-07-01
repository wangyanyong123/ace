package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoVo;
import com.github.wxiaoqi.security.app.entity.BizCrmProject;
import com.github.wxiaoqi.security.app.vo.city.out.ProjectInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
public interface BizCrmProjectMapper extends CommonMapper<BizCrmProject> {

	List<BizCrmProject> getByIds(@Param("ids")List<String> projectIds);

	List<ProjectInfoVo> getProjectListByCityId(@Param("cityId")String cityId);

	List<SysProjectInfoVo> sysProjectInfo();

//	int updateBatch(@Param("updateProjectList")List<BizCrmProject> updateProjectList);
//
//	int insertBatch(@Param("addProjectList")List<BizCrmProject> addProjectList);

	String selectProjectByUserId(@Param("userId") String userId);

    List<String> selectProjectIdListByCCodeList(@Param("cityCodeList") List<String> cityCodeList);

	int count();

}
