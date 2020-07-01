package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.SysModuleBiz;
import com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo.ProjectModuleParam;
import com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo.ProjectModuleSortParam;
import com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo.ProjectModuleRTPCBean;
import com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo.ResultModuleVo;
import com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo.ResultProjectModuleVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:11 2018/11/14
 * @Modified By:
 */
@RestController
@RequestMapping("web/sysModule")
@CheckClientToken
@CheckUserToken
@Api(tags="系统模块管理")
public class SysModuleController {

     @Autowired
     private SysModuleBiz sysModuleBiz;

    /**
     * 获取所有的模块和该项目选中的模块
     * @param projectId(需要参数:projectId)
     * @return
     */
    @RequestMapping(value = "/getProjectModulesForPC" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有的模块和该项目选中的模块---PC端", notes = "获取所有的模块和该项目选中的模块---PC端",httpMethod = "GET")
    @ApiImplicitParams({
       @ApiImplicitParam(name="system",value="对应系统(1-客户端APP,2-服务端APP)",dataType="String",required = true ,paramType = "query",example="4"),
       @ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true ,paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultModuleVo> getProjectModulesForPC(String projectId, String system){
        List<ResultModuleVo> moduleList =  sysModuleBiz.getProjectModulesForPC(projectId, system);
        return new TableResultResponse<ResultModuleVo>(moduleList.size(),moduleList);
    }


    /**
     * 保存项目模块
     * @param params（需要参数：projectId,projectModulesInfo）
     * @return
     */
    @RequestMapping(value = "/saveProjectModulesForPC",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存项目模块---PC端", notes = "保存项目模块---PC端",httpMethod = "POST")
    public ObjectRestResponse<Boolean> saveProjectModulesForPC(@RequestBody @ApiParam ProjectModuleParam params){
        String projectId = params.getProjectId();
        String system = params.getSystem();
        String[] projectModulesInfo = params.getProjectModulesInfo();
        this.sysModuleBiz.saveProjectModulesForPC(projectId, system, projectModulesInfo);
        return new ObjectRestResponse<>().data(true);
    }

    /**
     * 查询当前模块下勾选的子模块
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getProjectModulesDetail",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前模块下勾选的子模块---PC端", notes = "查询当前模块下勾选的子模块---PC端",httpMethod = "GET")
    @ApiImplicitParams({
         @ApiImplicitParam(name="system",value="对应系统(1-客户端APP,2-服务端APP)",dataType="String",required = true ,paramType = "query",example="4"),
         @ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true ,paramType = "query",example="1sdsgsfdghsfdgsd"),
         @ApiImplicitParam(name="moduleId",value="模块id",dataType="String",required = true ,paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultProjectModuleVo> getProjectModulesDetail(String projectId,String moduleId,String system){
        List<ResultProjectModuleVo> currentModuleList = sysModuleBiz.getProjectModulesDetail(projectId, moduleId, system);
        if(currentModuleList == null || currentModuleList.size() == 0){
            ResultProjectModuleVo vo = new ResultProjectModuleVo();
            vo.setModuleFullName("");
            vo.setModuleSort("");
            List<ProjectModuleRTPCBean> list = new ArrayList<>();
            vo.setChildren(list);
            currentModuleList.add(vo);
        }
        return new TableResultResponse<ResultProjectModuleVo>(currentModuleList.size(),currentModuleList);
    }


    /**
     * 更新项目模块的排序
     * @param param(需要参数：moduleSortInfo,projectId)
     * @return
     */
    @RequestMapping(value = "/updateModuleSortForPC" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新项目模块的排序---PC端", notes = "更新项目模块的排序---PC端",httpMethod = "POST")
    public ObjectRestResponse<Boolean> updateModuleSortForPC(@RequestBody @ApiParam ProjectModuleSortParam param){
        this.sysModuleBiz.updateModuleSortForPC(param);
        return new ObjectRestResponse<>().data(true);
    }






}
