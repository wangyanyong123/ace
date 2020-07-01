package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizProductOrderDiscountBiz;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDiscount;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@RestController
@RequestMapping("bizProductOrderDiscount")
@CheckClientToken
@CheckUserToken
public class BizProductOrderDiscountController extends BaseController<BizProductOrderDiscountBiz,BizProductOrderDiscount,String> {

}