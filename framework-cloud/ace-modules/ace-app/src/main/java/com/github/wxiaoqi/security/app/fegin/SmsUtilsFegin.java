package com.github.wxiaoqi.security.app.fegin;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-tool" , configuration = FeignApplyConfiguration.class)
public interface SmsUtilsFegin {



	@RequestMapping(value ="/smsService/sendMsg",method = RequestMethod.GET)
	public ObjectRestResponse sendMsg(@RequestParam(value = "mobilePhone", required = false) String mobilePhone,
                                      @RequestParam(value = "num", required = false) Integer num,
                                      @RequestParam(value = "lostSecond", required = false) Integer lostSecond,
                                      @RequestParam(value = "bizType", required = false, defaultValue = "2") String bizType,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "userId", required = false) String userId,
                                      @RequestParam("msgTheme") String msgTheme,
                                      @RequestParam(value = "msgParam", required = false) String msgParam);

}