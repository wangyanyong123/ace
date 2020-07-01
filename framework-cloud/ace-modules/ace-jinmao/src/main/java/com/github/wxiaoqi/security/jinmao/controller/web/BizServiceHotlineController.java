package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizServiceHotlineBiz;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.InputParam.SaveHotlineParam;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultHotlineInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultHotlineVo;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/bizServiceHotline")
@CheckClientToken
@CheckUserToken
@Api(tags = "热线服务")
public class BizServiceHotlineController{

    @Autowired
    private BizServiceHotlineBiz bizServiceHotlineBiz;

    /**
     * 查询服务热线列表
     * @return
     */
    @RequestMapping(value = "/getHotlineListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务热线列表---PC端", notes = "查询服务热线列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据标题模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultHotlineVo> getHotlineListPc(String projectId, String searchVal, Integer page, Integer limit){
        List<ResultHotlineVo> hotlineList = bizServiceHotlineBiz.getHotlineList(projectId, searchVal, page, limit);
        return new TableResultResponse<ResultHotlineVo>(hotlineList.size(), hotlineList);
    }



    /**
     * 删除服务热线
     * @param id
     * @return
     */
    @RequestMapping(value = "/delHotlineInfoPc",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除服务热线---PC端", notes = "删除服务热线---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delHotlineInfoPc(String id){
        return bizServiceHotlineBiz.delHotlineInfo(id);
    }



    /**
     * 保存服务热线
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveHotlinePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存服务热线---PC端", notes = "保存服务热线---PC端",httpMethod = "POST")
    public ObjectRestResponse saveHotlinePc(@RequestBody @ApiParam SaveHotlineParam params){
        return bizServiceHotlineBiz.saveHotline(params);
    }


    /**
     * 查询服务热线详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getHotlineInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务热线详情---PC端", notes = "查询服务热线详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultHotlineInfoVo> getHotlineInfoPc(@PathVariable String id){
        List<ResultHotlineInfoVo> info = bizServiceHotlineBiz.getHotlineInfo(id);
        return new TableResultResponse<ResultHotlineInfoVo>(info.size(),info);
    }


    /**
     * 编辑服务热线
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateHotlinePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑服务热线---PC端", notes = "编辑服务热线---PC端",httpMethod = "POST")
    public ObjectRestResponse updateHotlinePc(@RequestBody @ApiParam SaveHotlineParam params){
        return bizServiceHotlineBiz.updateHotline(params);
    }


    /**
     * 根据租户id查询项目
     * @return
     */
    @RequestMapping(value = "/getProjectListPC",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据租户id查询项目---PC端", notes = "根据租户id查询项目---PC端",httpMethod = "GET")
    public TableResultResponse<ProjectListVo> getProjectListPC(){
        List<ProjectListVo> projectList =  bizServiceHotlineBiz.getProjectList();
        return new TableResultResponse<ProjectListVo>(projectList.size(), projectList);
    }



}