package com.github.wxiaoqi.sms.controller;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.sms.biz.SysMsgThemeBiz;
import com.github.wxiaoqi.sms.entity.SysMsgTheme;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 *
 * @author zxl
 * @Date 2018-11-20 14:56:24
 */
@RestController
@RequestMapping("sysMsgTheme")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class SysMsgThemeController extends BaseController<SysMsgThemeBiz,SysMsgTheme,String> {

}