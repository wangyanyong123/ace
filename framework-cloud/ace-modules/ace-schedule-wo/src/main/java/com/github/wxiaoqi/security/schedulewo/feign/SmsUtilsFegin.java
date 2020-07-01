package com.github.wxiaoqi.security.schedulewo.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.schedulewo.vo.DoOperateByTypeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "ace-tool" , configuration = FeignApplyConfiguration.class)
public interface SmsUtilsFegin {



	@RequestMapping(value ="/smsService/sendMsg",method = RequestMethod.GET)
	public ObjectRestResponse sendMsg(@RequestParam(value ="mobilePhone",required = false) String mobilePhone,
									  @RequestParam(value = "num" ,required = false) Integer num,
									  @RequestParam(value = "lostSecond" ,required = false) Integer lostSecond,
									  @RequestParam(value = "bizType",required = false ,defaultValue = "2") String bizType,
									  @RequestParam(value = "email",required = false) String email,
									  @RequestParam(value = "userId",required = false) String userId,
									  @RequestParam("msgTheme") String msgTheme,
									  @RequestParam(value = "msgParam",required = false) String msgParam);

}