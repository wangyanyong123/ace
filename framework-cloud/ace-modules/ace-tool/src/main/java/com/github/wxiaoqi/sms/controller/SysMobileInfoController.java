package com.github.wxiaoqi.sms.controller;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.sms.biz.SysMobileInfoBiz;
import com.github.wxiaoqi.sms.entity.SysMobileInfo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 手机信息
 *
 * @author zxl
 * @Date 2018-11-20 16:04:25
 */
@RestController
@RequestMapping("sysMobileInfo")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class SysMobileInfoController extends BaseController<SysMobileInfoBiz,SysMobileInfo,String> {

}