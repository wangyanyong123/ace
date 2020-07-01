package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizEvaluateHousekeeperBiz;
import com.github.wxiaoqi.security.app.vo.evaluate.in.SaveHousekeeperEvaluate;
import com.github.wxiaoqi.security.app.vo.evaluate.out.DictValueVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管家评价表
 *
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
@RestController
@RequestMapping("bizEvaluateHousekeeper")
@CheckClientToken
@CheckUserToken
@Api(tags = "管家评价")
public class BizEvaluateHousekeeperController {


    @Autowired
    private BizEvaluateHousekeeperBiz bizEvaluateHousekeeperBiz;



    /**
     * 保存管家评价
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveHousekeeperEvaluate" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存管家评价---APP端", notes = "保存管家评价---APP端",httpMethod = "POST")
    public ObjectRestResponse saveHousekeeperEvaluate(@RequestBody @ApiParam SaveHousekeeperEvaluate param){
        return  bizEvaluateHousekeeperBiz.saveHousekeeperEvaluate(param);
    }



    /**
     * 查询首页管家评价详情
     * @return
     */
    @RequestMapping(value = "/getHousekeeperInfo" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询首页管家评价详情---APP端", notes = "查询首页管家评价详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getHousekeeperInfo(String projectId){
        return  bizEvaluateHousekeeperBiz.getHousekeeperInfo(projectId);
    }



    /**
     * 根据编码查询字典值
     * @return
     */
    @RequestMapping(value = "/getDictValue" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据编码查询字典值---APP端", notes = "根据编码查询字典值---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="code",value="编码",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<List<DictValueVo>> getDictValue(String code){
        return  bizEvaluateHousekeeperBiz.getDictValue(code);
    }



}