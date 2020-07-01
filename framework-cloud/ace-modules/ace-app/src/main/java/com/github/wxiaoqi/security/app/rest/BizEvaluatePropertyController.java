package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizEvaluatePropertyBiz;
import com.github.wxiaoqi.security.app.vo.property.in.SavePropertyEvaluate;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 物业评价表
 *
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
@RestController
@RequestMapping("bizEvaluateProperty")
@CheckClientToken
@CheckUserToken
@Api(tags = "物业评价")
public class BizEvaluatePropertyController {

    @Autowired
    private BizEvaluatePropertyBiz bizEvaluatePropertyBiz;



    /**
     * 保存物业评价
     * @param param
     * @return
     */
    @RequestMapping(value = "/savePropertyEvaluate" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存物业评价---APP端", notes = "保存物业评价---APP端",httpMethod = "POST")
    public ObjectRestResponse savePropertyEvaluate(@RequestBody @ApiParam SavePropertyEvaluate param){
        return  bizEvaluatePropertyBiz.savePropertyEvaluate(param);
    }


    /**
     * 查询首页物业评价详情
     * @return
     */
    @RequestMapping(value = "/getHousekeeperInfo" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询首页物业评价详情---APP端", notes = "查询首页物业评价详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getHousekeeperInfo(String projectId){
        return  bizEvaluatePropertyBiz.getPropertyInfo(projectId);
    }


}