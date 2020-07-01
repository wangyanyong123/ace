package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.SysModule;
import com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo.ModuleSortParam;
import com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo.ModuleRTPCBeanVo;
import com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo.ProjectModuleRTPCBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:08 2018/11/14
 * @Modified By:
 */
public interface SysModuleMapper extends CommonMapper<SysModule> {

      /**
       * 获取所有的模块和该项目选中的模块
       * @param system
       * @return
       */
      List<ModuleRTPCBeanVo> selectAllModules(@Param("projectId") String projectId, @Param("system") String system);

      List<ProjectModuleRTPCBean> selectProjectModules(@Param("projectId") String projectId, @Param("system") String system);

      /**
       * 查询当前项目的当前模块
       * @param projectId
       * @param moduleId
       * @return
       */
      ProjectModuleRTPCBean
      selectByIdAndProjectId(@Param("projectId") String projectId,@Param("moduleId") String moduleId);

     /**
     * 根据code，length获取父级节点模块
	 * @param code
	 * @param length
	 * @return
     */
      List<ProjectModuleRTPCBean> selectParentModulesByCode(@Param("code")String code,@Param("length")Integer length,@Param("system")String system);

      /**
       * 根据projectId和pid获取子元素的名称，sort
       * @param projectId
       * @param pid
       * @return
       */
      List<ProjectModuleRTPCBean> selectChildrenModule(@Param("projectId")String projectId,@Param("pid")String pid);

      String selectModulePidByModuleId(@Param("moduleId")String moduleId);


      /**
       * 更新项目模块
       * @return
       */
      int updateModuleProject(ModuleSortParam param);

}
