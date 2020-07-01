package com.github.wxiaoqi.security.auth.module.client.controller;

import com.github.wxiaoqi.security.auth.module.client.biz.GatewayRouteBiz;
import com.github.wxiaoqi.security.auth.module.client.entity.GatewayRoute;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gatewayRoute")
public class GatewayRouteController extends BaseController<GatewayRouteBiz, GatewayRoute, String> {

}