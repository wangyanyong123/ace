package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizExpressCompanyBiz;
import com.github.wxiaoqi.security.merchant.entity.BizExpressCompany;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 快递公司
 *
 * @author wangyanyong
 * @Date 2020-04-24 16:13:06
 */
@RestController
@RequestMapping("bizExpressCompany")
@CheckClientToken
@CheckUserToken
public class BizExpressCompanyController extends BaseController<BizExpressCompanyBiz,BizExpressCompany,String> {

    /**
     * 快递公司
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @GetMapping("queryExpressCompanyList")
    @ApiOperation(value = "我的商品订单", notes = "我的商品订单",httpMethod = "GET")
    public ObjectRestResponse queryExpressCompanyList(){
        return this.baseBiz.queryExpressCompanyList();
    }
}