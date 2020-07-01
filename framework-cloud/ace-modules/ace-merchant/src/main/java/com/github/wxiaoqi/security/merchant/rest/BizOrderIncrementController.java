package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizOrderIncrementBiz;
import com.github.wxiaoqi.security.merchant.entity.BizOrderIncrement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 
 *
 * @author wangyanyong
 * @Date 2020-04-26 16:21:55
 */
@RestController
@RequestMapping("bizOrderIncrement")
@CheckClientToken
@CheckUserToken
public class BizOrderIncrementController extends BaseController<BizOrderIncrementBiz,BizOrderIncrement,String> {

}