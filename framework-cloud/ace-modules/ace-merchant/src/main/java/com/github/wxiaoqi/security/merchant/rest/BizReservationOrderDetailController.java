package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizReservationOrderDetailBiz;
import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderDetail;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

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