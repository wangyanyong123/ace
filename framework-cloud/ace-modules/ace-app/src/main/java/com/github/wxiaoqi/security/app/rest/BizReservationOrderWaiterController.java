package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizReservationOrderWaiterBiz;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderWaiter;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 22:28:30
 */
@RestController
@RequestMapping("bizReservationOrderWaiter")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderWaiterController extends BaseController<BizReservationOrderWaiterBiz, BizReservationOrderWaiter,String> {

}