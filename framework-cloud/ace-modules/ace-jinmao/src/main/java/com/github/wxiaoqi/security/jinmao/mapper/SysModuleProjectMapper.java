package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.SysModuleProject;
import com.github.wxiaoqi.security.jinmao.vo.AppResultModuleVo.ResultAppModule;
import com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo.ProjectModuleRTPCBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:09 2018/11/14
 * @Modified By:
 */
public interface SysModuleProjectMapper extends CommonMapper<SysModuleProject> {
    /**
     * 删除没有选中模块
     * @param projectModule
     * @return
     */
    int deleteProjectModule(ProjectModuleRTPCBean projectModule);

    int insertModulesProject(SysModuleProject moduleProject);

    /**
     * App---查询模块
     * @param projectId
     * @param system
     * @return
     */
    List<ResultAppModule> getModules(@Param("pid") String pid, @Param("projectId") String projectId,
                                     @Param("system") String system);

    String selectMoudlePid();

    int delModule(String projectId);
}

