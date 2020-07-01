package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizReservationDeliveryBiz;
import com.github.wxiaoqi.security.app.entity.BizReservationDelivery;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 预约服务配送范围
 *
 * @author guohao
 * @Date 2020-06-11 12:21:50
 */
@RestController
@RequestMapping("bizReservationDelivery")
@CheckClientToken
@CheckUserToken
public class BizReservationDeliveryController extends BaseController<BizReservationDeliveryBiz,BizReservationDelivery,String> {

}