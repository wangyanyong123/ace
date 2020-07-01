package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户邮费关联表
 *
 * @author huangxl
 * @Date 2019-04-28 16:27:46
 */
@RestController
@RequestMapping("baseTenantPostage")
@CheckClientToken
@CheckUserToken
public class BaseTenantPostageController  {



}