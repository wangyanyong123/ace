package com.github.wxiaoqi.log.rest;

import com.github.wxiaoqi.log.biz.BizLoginLogBiz;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author ：wangjl
 * @date ：Created in 2019/9/12 14:56
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("appUserLive")
@CheckClientToken
@CheckUserToken
@Api(tags="用户活跃度统计")
public class AppUserLiveController {

    @Autowired
    private BizLoginLogBiz loginLogBiz;

    @RequestMapping(value = "/getUserLivenessCount",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "用户活跃度统计---PC端", notes = "用户活跃度统计---PC端",httpMethod = "GET")
    public ObjectRestResponse getUserLivenessCount(String projectId, String dateType,String year,String mouth,String week,String startTime,String endTime){
        return loginLogBiz.getUserLivenessCount(projectId,dateType,year,mouth,week,startTime,endTime);
    }
}
