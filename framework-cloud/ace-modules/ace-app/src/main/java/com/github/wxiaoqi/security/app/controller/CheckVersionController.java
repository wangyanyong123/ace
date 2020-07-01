package com.github.wxiaoqi.security.app.controller;

import com.github.wxiaoqi.security.app.biz.CheckVersionBiz;
import com.github.wxiaoqi.security.app.vo.version.Client;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sys")
@CheckClientToken
@CheckUserToken
@Api(tags = "版本更新")
public class CheckVersionController {

    @Autowired
    private CheckVersionBiz checkVersionBiz;

    @RequestMapping(value = "/checkVersion",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "检查版本更新--APP", notes = "检查版本更新---APP",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse checkVersion(@RequestBody @ApiParam Client client) {
        return checkVersionBiz.checkVersion(client);
    }

}
