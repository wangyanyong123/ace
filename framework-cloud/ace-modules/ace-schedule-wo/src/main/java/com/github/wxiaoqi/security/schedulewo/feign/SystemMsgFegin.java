package com.github.wxiaoqi.security.schedulewo.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.schedulewo.vo.SmsNoticeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:02 2019/4/4
 * @Modified By:
 */
@FeignClient(value = "ace-app" , configuration = FeignApplyConfiguration.class)
public interface SystemMsgFegin {
	@RequestMapping(value = "/sysMsgNotice/saveSmsNotice" ,method = RequestMethod.POST)
	ObjectRestResponse saveSmsNotice(@RequestBody SmsNoticeVo smsNoticeVo);
}
