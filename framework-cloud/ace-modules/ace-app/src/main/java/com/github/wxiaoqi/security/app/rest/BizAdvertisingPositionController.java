package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizAdvertisingPositionBiz;
import com.github.wxiaoqi.security.app.config.AppDefaultConfig;
import com.github.wxiaoqi.security.app.vo.advertising.out.AdvertisingInfo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * 优选商城广告位
 *
 * @author zxl
 * @Date 2018-12-17 15:07:24
 */
@RestController
@RequestMapping("bizAdvertisingPosition")
@CheckClientToken
@CheckUserToken
@Api(tags = "优选商城广告位")
public class BizAdvertisingPositionController {

    @Autowired
    private AppDefaultConfig appDefaultConfig;
    @Autowired
    private BizAdvertisingPositionBiz bizAdvertisingPositionBiz;

    @RequestMapping(value = "/getAdvertInfoListApp", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询广告位---App端", notes = "查询广告位---App端", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name="projectId",value="社区ID",dataType="String",required = true ,paramType = "query",example="d4a5d4a65"),
        @ApiImplicitParam(name="position",value="位置",dataType="Integer",required = false ,paramType = "query",example="1")
    })

    public ObjectRestResponse<AdvertisingInfo> getAdvertInfoListApp( String projectId, Integer position) {
        return bizAdvertisingPositionBiz.getAdvertisinList(projectId, position);
    }


    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/getAdvertInfoList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询广告位---不需要登录", notes = "查询广告位---不需要登录", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name="projectId",value="社区ID",dataType="String",required = true ,paramType = "query",example="d4a5d4a65"),
        @ApiImplicitParam(name="position",value="位置",dataType="Integer",required = false ,paramType = "query",example="1")
    })
    public ObjectRestResponse<AdvertisingInfo> getAdvertInfoList(String projectId, Integer position, HttpServletRequest request) {
        if(StringUtils.isNotEmpty(projectId)){
            return bizAdvertisingPositionBiz.getAdvertisinList(projectId, position);
        }
        String cityCode = RequestHeaderUtil.getCityCode(request);
        return bizAdvertisingPositionBiz.getAdvertisinListWithCityCode(position,Collections.singletonList(cityCode));
    }
}
