package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.wechat.in.BindPhoneIn;
import com.github.wxiaoqi.security.api.vo.wechat.in.WechatAuthorizeIn;
import com.github.wxiaoqi.security.app.biz.BizUserWechatBiz;
import com.github.wxiaoqi.security.app.entity.BizUserWechat;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CheckUserToken
@CheckClientToken
@RestController
@RequestMapping("wechat")
public class BizUserWechatController extends BaseController<BizUserWechatBiz, BizUserWechat, String> {

    @Autowired
    private BizUserWechatBiz bizUserWechatBiz;


    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信公众号、小程序授权", notes = "微信公众号、小程序授权", httpMethod = "POST")
    public ObjectRestResponse authorize(@RequestBody WechatAuthorizeIn authorizeIn,
                                        HttpServletRequest request) {
        authorizeIn.setAppType(RequestHeaderUtil.getPlatformIntValue(request));
        authorizeIn.check();
        return bizUserWechatBiz.authorize(authorizeIn);
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/bindPhone", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "绑定手机号", notes = "绑定手机号", httpMethod = "POST")
    public ObjectRestResponse bindPhone(@RequestBody BindPhoneIn bindPhoneIn) {
        bindPhoneIn.setAppType(RequestHeaderUtil.getPlatformIntValue(request));
        bindPhoneIn.check();
        return bizUserWechatBiz.bindPhone(bindPhoneIn);
    }

    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    @ApiOperation(value = "绑定用户", notes = "绑定用户", httpMethod = "POST")
    public ObjectRestResponse bindUser(@RequestBody BindPhoneIn bindPhoneIn,
                                       HttpServletRequest request) {
        bindPhoneIn.setAppType(RequestHeaderUtil.getPlatformIntValue(request));
        bindPhoneIn.check();
        return bizUserWechatBiz.bindPhone(bindPhoneIn);
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @GetMapping("sendSmsCode")
    @ApiOperation(value = "发送绑定短信验证码", notes = "发送绑定短信验证码", httpMethod = "GET")
    @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", required = true, paramType = "query", example = "138****1234")
    public ObjectRestResponse getValidCode(@RequestParam String phone) {
        return bizUserWechatBiz.sendSmsCode(phone);
    }
}