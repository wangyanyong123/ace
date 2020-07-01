package com.github.wxiaoqi.security.external.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.external.biz.BizExternalMenuBiz;
import com.github.wxiaoqi.security.external.entity.BizExternalMenu;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 对外提供接口表
 *
 * @author zxl
 * @Date 2018-12-25 18:23:09
 */
@RestController
@RequestMapping("bizExternalMenu")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class BizExternalMenuController extends BaseController<BizExternalMenuBiz,BizExternalMenu,String> {

}