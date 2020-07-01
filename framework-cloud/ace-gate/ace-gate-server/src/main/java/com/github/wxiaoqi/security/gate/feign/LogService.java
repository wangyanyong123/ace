package com.github.wxiaoqi.security.gate.feign;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import com.github.wxiaoqi.security.gate.config.FeignConfiguration;
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
@FeignClient(value = "ace-tool", configuration = FeignConfiguration.class)
public interface LogService {
	@RequestMapping(value = "/log/savelog", method = RequestMethod.POST)
	ObjectRestResponse savelog(@RequestBody LogInfoVo logInfoVo);
}
