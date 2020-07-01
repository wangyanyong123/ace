package com.github.wxiaoqi.security.merchant.fegin;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:05 2019/4/9
 * @Modified By:
 */
@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface LogService {
	@RequestMapping(value = "/log/savelog", method = RequestMethod.POST)
	ObjectRestResponse savelog(@RequestBody LogInfoVo logInfoVo);
}
