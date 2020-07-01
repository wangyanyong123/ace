package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券使用表
 *
 * @Date 2019-04-16 10:49:40
 */
@RestController
@RequestMapping("bizCouponUse")
@CheckClientToken
@CheckUserToken
public class BizCouponUseController {

}