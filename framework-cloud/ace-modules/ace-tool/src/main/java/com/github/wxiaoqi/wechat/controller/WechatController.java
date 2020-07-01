package com.github.wxiaoqi.wechat.controller;

import com.github.wxiaoqi.security.api.vo.wechat.in.WechatAuthorizeIn;
import com.github.wxiaoqi.security.api.vo.wechat.out.WechatOpenIdResult;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.github.wxiaoqi.wechat.biz.WechatBiz;
import com.github.wxiaoqi.wechat.biz.WechatBizFactory;
import com.github.wxiaoqi.wechat.jssdk.jssdk.JSSDKAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: guohao
 * @create: 2020-04-12 12:04
 **/
@RequestMapping("/wechat")
@RestController
@Api(tags = "微信相关接口")
@Slf4j
public class WechatController {

    @Autowired
    private WechatBizFactory wechatBizFactory;

    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/getAuthorizeUrl", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取微信公众号授权url", notes = "获取微信公众号授权url", httpMethod = "GET")
    public ObjectRestResponse authorize(@RequestParam(defaultValue = "1") int scopeType, @RequestParam String notifyUrl, String state,
                                        HttpServletRequest request) {
        Integer appType = RequestHeaderUtil.getPlatformIntValue(request);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (!AceDictionary.APP_TYPE_H5.equals(appType)) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("只有公众号需要获取授权url");
            return objectRestResponse;
        }
        String scope = scopeType == 1 ? "snsapi_base" : "snsapi_userinfo";
        WechatBiz commonsBiz = wechatBizFactory.getCommonsBiz(appType);
        String userAuthorizationURL = commonsBiz.getUserAuthorizationURL(notifyUrl, scope, state);
        Map<String, String> result = new HashMap<>();
        result.put("url", userAuthorizationURL);
        objectRestResponse.setData(result);
        return objectRestResponse;

    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信公众号、小程序授权", notes = "微信公众号、小程序授权", httpMethod = "POST")
    public ObjectRestResponse<WechatOpenIdResult> authorize(@RequestBody WechatAuthorizeIn authorizeIn,
                                                            HttpServletRequest request) {
//        authorizeIn.setAppType(AppTypeUtil.getAppType(request));
        authorizeIn.check();
        WechatBiz commonsBiz = wechatBizFactory.getCommonsBiz(authorizeIn.getAppType());
        return commonsBiz.getOpenId(authorizeIn.getCode());
    }

    @RequestMapping(value = "/getJsSdkConfig", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取微信jssdk配置信息", notes = "获取微信jssdk配置信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="url",value="页面地址",dataType="String",required = true ,paramType = "query"),
            @ApiImplicitParam(name="jssdkapis",value="jssdk接口",dataType="String",required = true ,paramType = "query",example = ""),
    })
    public ObjectRestResponse getJsSdkConfig(String url, JSSDKAPI[] jssdkapis, boolean debug,
                                             HttpServletRequest request) {
        Integer platform = RequestHeaderUtil.getPlatformIntValue(request);
        WechatBiz commonsBiz = wechatBizFactory.getCommonsBiz(platform);
        return commonsBiz.getJsSdkConfig(url, jssdkapis, debug);
    }
}
