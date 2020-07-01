package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCrmProject;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目表
 * 
 * @author zxl
 * @Date 2018-12-11 11:12:47
 */
public interface BizCrmProjectMapper extends CommonMapper<BizCrmProject> {

    /**
     * 查询所有的项目列表
     * @return
     */
    List<ProjectListVo> selectProjectName();

    ProjectInfoVo selectProjectById(@Param("projectId") String projectId);

    /**
     * 查询项目树
     * @return
     */
    List<ProjectTreeVo> selectProjectTreeList(@Param("projectId")String projectId);

    List<UnitInfoVo> selectChosenUnitByEnclosedId(@Param("enclosedId") String enclosedId);
}
