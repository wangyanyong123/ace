package com.github.wxiaoqi.feign;

import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ace-app" , configuration = FeignApplyConfiguration.class)
public interface CommodityFeign {


    /**
     * 支付宝、微信回调通知成功后业务处理
     * @param payOrderFinishIn 参数
     * @return
     */
    @RequestMapping(value = "/commodity/doPayOrderFinish" ,method = RequestMethod.POST)
    public ObjectRestResponse doPayOrderFinish(@RequestBody @ApiParam PayOrderFinishIn payOrderFinishIn);


}