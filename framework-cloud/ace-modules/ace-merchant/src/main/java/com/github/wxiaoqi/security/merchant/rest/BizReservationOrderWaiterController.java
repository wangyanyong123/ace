package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizReservationOrderWaiterBiz;
import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderWaiter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 服务订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 17:50:27
 */
@RestController
@RequestMapping("bizReservationOrderWaiter")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderWaiterController extends BaseController<BizReservationOrderWaiterBiz,BizReservationOrderWaiter,String> {

}