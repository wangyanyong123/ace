package com.github.wxiaoqi.sms.controller;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.sms.biz.SysSmsBiz;
import com.github.wxiaoqi.sms.entity.SysSms;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 短信发送
 *
 * @author zxl
 * @Date 2018-11-20 18:51:20
 */
@RestController
@RequestMapping("sysSms")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class SysSmsController extends BaseController<SysSmsBiz,SysSms,String> {

}