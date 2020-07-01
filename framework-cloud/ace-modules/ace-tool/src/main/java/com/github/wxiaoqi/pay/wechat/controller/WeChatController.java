package com.github.wxiaoqi.pay.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.pay.biz.NotifySuccessBiz;
import com.github.wxiaoqi.pay.biz.RefundBiz;
import com.github.wxiaoqi.pay.biz.support.NotifySuccessParam;
import com.github.wxiaoqi.pay.wechat.service.WeChatPayService;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.api.vo.to.ApplyRefundTO;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:00 2018/12/7
 * @Modified By:
 */
@RestController
@RequestMapping("/wechatPay")
@Api(tags = "微信支付回调")
@Slf4j
public class WeChatController {

    @Autowired
    private WeChatPayService weChatPayService;
    @Autowired
    private RefundBiz refundBiz;

    @RequestMapping("/paynotify")
    public void paynotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            InputStream inputStream = request.getInputStream();
            String notifyXml = new String(IOUtils.toByteArray(inputStream), StandardCharsets.UTF_8);
            String paynotify = weChatPayService.paynotify(notifyXml);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            writer.println(paynotify);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("微信支付结果通知接口服务端异常,异常信息---" + e.getMessage(), e);
        } finally {
            writer.close();
        }
    }

    @Autowired
    private NotifySuccessBiz notifySuccessBiz;

    @RequestMapping("/paynotifyLocal")
    public void paynotify(NotifySuccessParam param) throws IOException {
        notifySuccessBiz.doNotifySuccessBusiness(param);
    }

    @PostMapping(value = "/refund")
    @ApiOperation(value = "微信退款", notes = "微信退款", httpMethod = "POST")
    @CheckUserToken
    public ObjectRestResponse refund(@RequestBody @ApiParam ApplyRefundTO applyRefundTO) {
        return refundBiz.refund(applyRefundTO);
    }

    @PostMapping(value = "/pay")
    @ApiOperation(value = "微信支付", notes = "微信支付", httpMethod = "POST")
    @CheckUserToken
    public ObjectRestResponse pay(@RequestBody @ApiParam GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        generateUnifiedOrderIn.check();
        Assert.hasLength(generateUnifiedOrderIn.getOpenId(), "openid 不能为空");
        Assert.hasLength(generateUnifiedOrderIn.getUserIp(), "客服端ip 不能为空");
        return weChatPayService.pay(generateUnifiedOrderIn);
    }

}
