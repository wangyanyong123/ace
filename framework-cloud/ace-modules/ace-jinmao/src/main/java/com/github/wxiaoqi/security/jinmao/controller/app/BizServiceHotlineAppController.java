package com.github.wxiaoqi.security.jinmao.controller.app;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizServiceHotlineBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hotline")
@CheckClientToken
@CheckUserToken
@Api(tags = "热线服务--App")
public class BizServiceHotlineAppController {

    @Autowired
    private BizServiceHotlineBiz bizServiceHotlineBiz;

    /**
     * 查询服务热线列表
     * @return
     */
    @RequestMapping(value = "/getAppHotlineList/{projectId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务热线列表---App端", notes = "查询服务热线列表---App端",httpMethod = "GET")
    public ObjectRestResponse getAppHotlineList(@PathVariable  String projectId){
        return bizServiceHotlineBiz.getAppHotlineList(projectId);
    }


}