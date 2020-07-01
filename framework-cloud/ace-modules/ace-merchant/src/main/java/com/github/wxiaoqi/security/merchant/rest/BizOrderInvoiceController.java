package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizOrderInvoiceBiz;
import com.github.wxiaoqi.security.merchant.entity.BizOrderInvoice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 
 *
 * @author wangyanyong
 * @Date 2020-04-26 09:10:30
 */
@RestController
@RequestMapping("bizOrderInvoice")
@CheckClientToken
@CheckUserToken
public class BizOrderInvoiceController extends BaseController<BizOrderInvoiceBiz,BizOrderInvoice,String> {

}