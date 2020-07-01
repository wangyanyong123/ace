package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BaseTenantBusinessBiz;
import com.github.wxiaoqi.security.jinmao.entity.BaseTenantBusiness;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 
 *
 * @author zxl
 * @Date 2018-12-06 11:38:06
 */
@RestController
@RequestMapping("baseTenantBusiness")
@CheckClientToken
@CheckUserToken
public class BaseTenantBusinessController extends BaseController<BaseTenantBusinessBiz,BaseTenantBusiness,String> {

}