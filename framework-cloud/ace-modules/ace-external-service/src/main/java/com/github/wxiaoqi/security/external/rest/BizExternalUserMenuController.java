package com.github.wxiaoqi.security.external.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.external.biz.BizExternalUserMenuBiz;
import com.github.wxiaoqi.security.external.entity.BizExternalUserMenu;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 对外提供和接口关系表
 *
 * @author zxl
 * @Date 2018-12-25 18:23:09
 */
@RestController
@RequestMapping("bizExternalUserMenu")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class BizExternalUserMenuController extends BaseController<BizExternalUserMenuBiz,BizExternalUserMenu,String> {

}