package com.github.wxiaoqi.pay.controller;

import com.github.wxiaoqi.pay.biz.PayBiz;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.IpUtils;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author guohao
 * @Date 2020/4/12 11:14
 */
@RestController
@RequestMapping("/pay")
@Api(tags = "支付接口入口")
@Slf4j
public class PayController {

    @Autowired
    private PayBiz payBiz;

    @PostMapping(value = "/generateUnifiedOrder")
    @ApiOperation(value = "支付", notes = "支付", httpMethod = "POST")
    @CheckUserToken
    public ObjectRestResponse generateUnifiedOrder(@RequestBody @ApiParam GenerateUnifiedOrderIn generateUnifiedOrderIn, HttpServletRequest request) {
        generateUnifiedOrderIn.check();
        if (generateUnifiedOrderIn.getAppType() == null) {
            generateUnifiedOrderIn.setAppType(RequestHeaderUtil.getPlatformIntValue(request));
        }
        if (StringUtils.isEmpty(generateUnifiedOrderIn.getUserIp())) {
            generateUnifiedOrderIn.setUserIp(IpUtils.getRequestIpAddress(request));
        }
        return payBiz.pay(generateUnifiedOrderIn);
    }
}
