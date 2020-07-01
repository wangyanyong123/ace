package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.SysModule;
import com.github.wxiaoqi.security.jinmao.entity.SysModuleProject;
import com.github.wxiaoqi.security.jinmao.mapper.SysModuleMapper;
import com.github.wxiaoqi.security.jinmao.mapper.SysModuleProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo.ModuleSortParam;
import com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo.ProjectModuleSortParam;
import com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:13 2018/11/14
 * @Modified By:
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SysModuleBiz extends BusinessBiz<SysModuleMapper,SysModule> {

    private Logger logger = LoggerFactory.getLogger(SysModuleBiz.class);

       @Autowired
       private  SysModuleMapper sysModuleMapper;
       @Autowired
       private SysModuleProjectMapper sysModuleProjectMapper;

    public List<ResultModuleVo> getProjectModulesForPC(String projectId, String system) {
        List<ResultModuleVo> resultVo = new ArrayList<>();
        ResultModuleVo result = new ResultModuleVo();
        List<ModuleRTPCBeanVo> allModulesList = sysModuleMapper.selectAllModules(projectId,system);

        if (allModulesList != null) {
            List<ModuleTree> trees = new ArrayList<>();
            allModulesList.forEach(modules -> {
                trees.add(new ModuleTree(modules.getModuleId(), modules.getPid(), modules.getName(), modules.getCode()));
            });
            List<ModuleTree> treesTemp = TreeUtil.bulid(trees, "-1", null);
            result.setChildren(treesTemp);
        }


        List<ProjectModuleRTPCBean> modulesList = sysModuleMapper.selectProjectModules(projectId, system);
        if (modulesList != null && modulesList.size()>0) {
            String projectModulesIds = "";
            StringBuilder resultEva = new StringBuilder();
            for (ProjectModuleRTPCBean temp : modulesList) {
                resultEva.append(temp.getModuleId() + ",");
            }
            projectModulesIds = resultEva.substring(0, resultEva.length() - 1);
            result.setProjectModulesIds(projectModulesIds.split(","));
        }
        resultVo.add(result);
        return resultVo;
    }




    /**
     * 保存项目模块
     * @param projectId（需要参数：projectId,projectModulesIds）
     * @return
     */
    public void saveProjectModulesForPC(String projectId, String system,String[] projectModulesIds){
        try {
            List<String> list = Arrays.asList(projectModulesIds);
            if(projectModulesIds != null){
                List<ModuleRTPCBeanVo> allModulesList = sysModuleMapper.selectAllModules(projectId,system);
                Map<String,ModuleRTPCBeanVo> allModulesMap = new HashMap<>();
                for(ModuleRTPCBeanVo temp:allModulesList){
                    allModulesMap.put(temp.getModuleId(),temp);
                }

                //删除关联数据
               if(sysModuleProjectMapper.delModule(projectId) < 0){
                   logger.error("删除关联数据失败!");
               }

                for (String id : list){
                        SysModuleProject result = new SysModuleProject();
                        ModuleRTPCBeanVo module = allModulesMap.get(id);
                        result.setId(UUIDUtils.generateUuid());
                        result.setModuleId(id);
                        result.setProjectId(projectId);
                        if(module.getCode().length() < 2){
                            result.setSort(-1);
                        }else{
                            result.setSort(Integer.parseInt(module.getCode().substring(module.getCode().length()-2, module.getCode().length())));
                        }
                        result.setStatus("1");
                        result.setCreateBy(BaseContextHandler.getUserID());
                        if (sysModuleProjectMapper.insertModulesProject(result) < 0){
                            logger.error("保存项目模块数据失败,moduleId为{},projectId为{}",id,projectId);
                        }
                }
            }


        } catch (Exception e) {
            logger.error("projectModulesIds数据解析失败",e);
        }
    }


    /**
     * 获取项目模块详情
     * @param moduleId
     * @param projectId
     * @return
     */
    public List<ResultProjectModuleVo> getProjectModulesDetail(String projectId, String moduleId, String system) {
        List<ResultProjectModuleVo> resultVo = new ArrayList<>();
        ResultProjectModuleVo result = new ResultProjectModuleVo();
        ProjectModuleRTPCBean tempModule = sysModuleMapper.selectByIdAndProjectId(projectId, moduleId);
        if(tempModule!=null){
            result.setModuleSort(tempModule.getSort() == null ? "" : tempModule.getSort());
            //设置模块全名
            if (!StringUtils.isEmpty(tempModule.getPid())) {
                String fullName = tempModule.getName();
                List<ProjectModuleRTPCBean> parentModules = sysModuleMapper.selectParentModulesByCode(tempModule.getCode().substring(0, 3), tempModule.getCode().length(), system);
                if (parentModules != null && parentModules.size() > 0) {
                    for (ProjectModuleRTPCBean temp : parentModules) {
                        fullName = temp.getName() + "-" + fullName;
                    }
                }
                result.setModuleFullName(fullName);
            } else {
                result.setModuleFullName(tempModule.getName());

            }


            //查询子节点
            List<ProjectModuleRTPCBean> childrenModules = sysModuleMapper.selectChildrenModule(projectId, moduleId);

            for (ProjectModuleRTPCBean temp : childrenModules) {
                List<ProjectModuleRTPCBean> selectChildrenModule = sysModuleMapper.selectChildrenModule(projectId, temp.getModuleId());
                if (selectChildrenModule == null || selectChildrenModule.size() == 0) {
                    temp.setIsChild("1");
                } else {
                    temp.setIsChild("0");
                }
            }
            result.setChildren(childrenModules);
            resultVo.add(result);
        }
        return resultVo;
    }


    /**
     * 更新项目模块的排序
     * @param moduleSortInfo(需要参数：moduleSortInfo)
     * @return
     */
     public void updateModuleSortForPC(ProjectModuleSortParam moduleSortInfo) {
        try {
            String projectId = moduleSortInfo.getProjectId();
            List<Map<String,String>> moduleSortList= moduleSortInfo.getModuleSortInfo();
            for (Map<String,String> module:moduleSortList) {
                ModuleSortParam param = new ModuleSortParam();
                param.setModuleId(module.get("moduleId"));
                param.setSort(module.get("sort"));
                param.setProjectId(projectId);
                if (sysModuleMapper.updateModuleProject(param) < 1) {
                    logger.error("更新项目模块数据失败,moduleId为{},projectId为{}", param.getModuleId(), projectId);
                }
            }
        } catch (Exception e) {
            logger.error("解析模块排序数据出错", e);
        }
    }






}