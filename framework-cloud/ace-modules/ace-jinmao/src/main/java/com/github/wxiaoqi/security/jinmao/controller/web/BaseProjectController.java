package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BaseProjectBiz;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("web/baseProject")
@CheckClientToken
@CheckUserToken
@Api(tags="获取项目管理")
public class BaseProjectController{

     @Autowired
     private BaseProjectBiz baseProjectBiz;

    /**
     * 获取项目列表
     * @return
     */
    @RequestMapping(value = "/getProjectListForPC",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取项目列表---PC端", notes = "获取项目列表---PC端",httpMethod = "GET")
    public TableResultResponse<ProjectListVo> getProjectListForPC(){
        List<ProjectListVo> projectList =  baseProjectBiz.getProjectListForPC();
        return new TableResultResponse<ProjectListVo>(projectList.size(), projectList);
    }
}