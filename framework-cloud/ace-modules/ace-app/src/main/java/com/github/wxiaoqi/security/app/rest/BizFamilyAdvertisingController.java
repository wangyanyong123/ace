package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizFamilyAdvertisingBiz;
import com.github.wxiaoqi.security.app.vo.advertising.out.FamilyAdVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 家里人广告位
 *
 * @author huangxl
 * @Date 2019-08-12 17:59:01
 */
@RestController
@RequestMapping("bizFamilyAdvertising")
@CheckClientToken
@CheckUserToken
@Api(tags = "APP家里人广告位")
public class BizFamilyAdvertisingController{

    @Autowired
    private BizFamilyAdvertisingBiz bizFamilyAdvertisingBiz;


    @RequestMapping(value = "/getFamilyAdListApp", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询app家里人广告位---App端", notes = "查询app家里人广告位---App端", httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<List<FamilyAdVo>> getFamilyAdListApp(String projectId) {
        return bizFamilyAdvertisingBiz.getFamilyAdList(projectId);
    }

}