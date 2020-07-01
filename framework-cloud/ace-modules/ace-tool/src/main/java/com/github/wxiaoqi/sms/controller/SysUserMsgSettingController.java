package com.github.wxiaoqi.sms.controller;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.sms.biz.SysUserMsgSettingBiz;
import com.github.wxiaoqi.sms.entity.SysUserMsgSetting;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 *
 * @author zxl
 * @Date 2018-11-20 16:27:29
 */
@RestController
@RequestMapping("sysUserMsgSetting")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class SysUserMsgSettingController extends BaseController<SysUserMsgSettingBiz,SysUserMsgSetting,String> {

}