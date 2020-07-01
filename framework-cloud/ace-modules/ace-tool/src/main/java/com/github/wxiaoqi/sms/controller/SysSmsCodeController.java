package com.github.wxiaoqi.sms.controller;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.sms.biz.SysSmsCodeBiz;
import com.github.wxiaoqi.sms.entity.SysSmsCode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 短信验证码
 *
 * @author zxl
 * @Date 2018-11-20 11:24:20
 */
@RestController
@RequestMapping("sysSmsCode")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class SysSmsCodeController extends BaseController<SysSmsCodeBiz,SysSmsCode,Long> {

}