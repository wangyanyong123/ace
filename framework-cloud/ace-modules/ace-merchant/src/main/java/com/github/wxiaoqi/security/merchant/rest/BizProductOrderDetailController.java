package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizProductOrderDetailBiz;
import com.github.wxiaoqi.security.merchant.entity.BizProductOrderDetail;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 订单产品表
 *
 * @author wangyanyong
 * @Date 2020-04-24 22:16:33
 */
@RestController
@RequestMapping("bizProductOrderDetail")
@CheckClientToken
@CheckUserToken
public class BizProductOrderDetailController extends BaseController<BizProductOrderDetailBiz,BizProductOrderDetail,String> {

}