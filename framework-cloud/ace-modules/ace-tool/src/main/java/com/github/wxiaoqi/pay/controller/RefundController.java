package com.github.wxiaoqi.pay.controller;

import com.github.wxiaoqi.pay.biz.RefundBiz;
import com.github.wxiaoqi.security.api.vo.to.ApplyRefundTO;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author guohao
 * @Date 2020/4/12 11:14
 */
@RestController
@RequestMapping("/refund")
@Api(tags = "退款相关接口")
@Slf4j
public class RefundController {

    @Autowired
    private RefundBiz refundBiz;

    @PostMapping(value = "/refund")
    @ApiOperation(value = "支付退款", notes = "支付退款", httpMethod = "POST")
    @CheckUserToken
    public ObjectRestResponse refund(@RequestBody @ApiParam ApplyRefundTO applyRefundTO) {
        return refundBiz.refund(applyRefundTO);
    }
}
