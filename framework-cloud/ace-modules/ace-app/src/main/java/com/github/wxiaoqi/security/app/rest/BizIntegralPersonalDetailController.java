package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizIntegralPersonalDetailBiz;
import com.github.wxiaoqi.security.app.vo.group.in.SaveIntegralInfo;
import com.github.wxiaoqi.security.app.vo.integral.IntegralPersonalVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个人积分明细表
 *
 * @author zxl
 * @Date 2018-12-28 10:17:42
 */
@RestController
@RequestMapping("bizAppIntegral")
@CheckClientToken
@CheckUserToken
@Api(tags = "我的积分")
public class BizIntegralPersonalDetailController {

    @Autowired
    private BizIntegralPersonalDetailBiz bizIntegralPersonalDetailBiz;


    /**
     * 查询用户积分账单
     * @return
     */
    @RequestMapping(value = "/getIntegralPersonalDetail" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户积分账单---APP端", notes = "查询用户积分账单---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="createTime",value="账单时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<IntegralPersonalVo>> getIntegralPersonalDetail(String createTime, Integer page, Integer limit){
        return  bizIntegralPersonalDetailBiz.getIntegralPersonalDetail(createTime, page, limit);
    }



    /**
     * 查询用户个人积分
     * @return
     */
    @RequestMapping(value = "/getUserIntegralInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户个人积分---APP端", notes = "查询用户个人积分---APP端",httpMethod = "GET")
    public ObjectRestResponse<String> getUserIntegralInfo(){
        return bizIntegralPersonalDetailBiz.getUserIntegralInfo();
    }

    /**
     * 公用加积分接口
     * @return
     */
    @RequestMapping(value = "/addPublicIntegarl", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "公用加积分接口---APP端", notes = "公用加积分接口---APP端",httpMethod = "POST")
    public ObjectRestResponse addPublicIntegarl(@RequestBody @ApiParam SaveIntegralInfo param){
        return bizIntegralPersonalDetailBiz.addPublicIntegarl(param.getRuleCode(),param.getGroupId(), param.getObjectId());
    }

}