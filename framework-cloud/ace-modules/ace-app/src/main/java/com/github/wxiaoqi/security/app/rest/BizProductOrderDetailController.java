package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizProductOrderDetailBiz;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDetail;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 订单产品表
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@RestController
@RequestMapping("bizProductOrderDetail")
@CheckClientToken
@CheckUserToken
public class BizProductOrderDetailController extends BaseController<BizProductOrderDetailBiz,BizProductOrderDetail,String> {

}