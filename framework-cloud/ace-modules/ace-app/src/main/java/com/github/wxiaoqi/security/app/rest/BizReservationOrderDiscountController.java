package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizReservationOrderDiscountBiz;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderDiscount;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 
 *
 * @author huangxl
 * @Date 2020-04-20 16:45:22
 */
@RestController
@RequestMapping("bizReservationOrderDiscount")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderDiscountController extends BaseController<BizReservationOrderDiscountBiz,BizReservationOrderDiscount,String> {

}