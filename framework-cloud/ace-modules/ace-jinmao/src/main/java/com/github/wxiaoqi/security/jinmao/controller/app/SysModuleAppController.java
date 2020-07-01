package com.github.wxiaoqi.security.jinmao.controller.app;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.biz.SysModuleProjectBiz;
import com.github.wxiaoqi.security.jinmao.vo.AppResultModuleVo.AppModuleTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("web/sysModuleApp")
@CheckClientToken
@CheckUserToken
@Api(tags="模块-app")
public class SysModuleAppController {

    @Autowired
    private SysModuleProjectBiz sysModuleProjectBiz;

    /**
     * 获取所有的模块和该项目选中的模块
     * @param projectId(需要参数:projectId)
     * @return
     */
    @RequestMapping(value = "/getModules" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取模块---App端", notes = "获取模块---App端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="system",value="对应系统(1-客户端APP,2-服务端APP)",dataType="String",required = true ,paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true ,paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<AppModuleTree>> getModules(String projectId, String system){
        return sysModuleProjectBiz.getModules(projectId,system);
    }
}
