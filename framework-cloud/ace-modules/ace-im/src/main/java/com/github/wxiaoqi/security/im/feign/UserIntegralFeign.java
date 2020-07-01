package com.github.wxiaoqi.security.im.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:30 2019/8/19
 * @Modified By:
 */
@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface UserIntegralFeign {
	@RequestMapping(value = "/bizUserIntegral/finishDailyTask", method = {RequestMethod.GET})
	ObjectRestResponse finishDailyTask(@RequestParam String taskCode, @RequestParam String userId) ;


}
