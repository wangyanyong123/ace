package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizReservationOrderDetailBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizReservationOrderDetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预约服务订单详情表
 *
 * @author wangyanyong
 * @Date 2020-04-24 22:16:54
 */
@RestController
@RequestMapping("bizReservationOrderDetail")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderDetailController extends BaseController<BizReservationOrderDetailBiz,BizReservationOrderDetail,String> {

}