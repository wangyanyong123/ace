package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizClassifyBiz;
import com.github.wxiaoqi.security.app.vo.classify.out.ClassifyVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类
 *
 * @Author guohao
 * @Date 2020/4/16 16:38
 */
@RestController
@RequestMapping("classify")
@CheckClientToken
@CheckUserToken
@Api(tags = "分类相关 -- 微信端")
public class BizClassifyController {
    @Autowired
    private BizClassifyBiz bizBusinessBiz;

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/findBusinessClassifyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询一级分类列表", notes = "查询一级分类列表", httpMethod = "GET")
    public ObjectRestResponse<ClassifyVo> findBusinessClassifyList() {
        List<ClassifyVo> businessClassifyList = bizBusinessBiz.findBusinessClassifyList();
        return ObjectRestResponse.ok(businessClassifyList);
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/findSecondClassifyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询二级分类列表", notes = "查询二级分类列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "busId", value = "一级分类id", dataType = "String", paramType = "query", required = true)
    })
    public ObjectRestResponse<ClassifyVo> findBusinessClassifyList(@RequestParam String busId) {
        List<ClassifyVo> businessClassifyList = bizBusinessBiz.findSecondClassifyList(busId);
        return ObjectRestResponse.ok(businessClassifyList);
    }
}