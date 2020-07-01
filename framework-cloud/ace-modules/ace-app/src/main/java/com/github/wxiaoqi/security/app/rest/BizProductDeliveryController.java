package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizProductDeliveryBiz;
import com.github.wxiaoqi.security.app.entity.BizProductDelivery;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 商品配送范围
 *
 * @author guohao
 * @Date 2020-04-24 22:33:09
 */
@RestController
@RequestMapping("bizProductDelivery")
@CheckClientToken
@CheckUserToken
public class BizProductDeliveryController extends BaseController<BizProductDeliveryBiz,BizProductDelivery,String> {

}