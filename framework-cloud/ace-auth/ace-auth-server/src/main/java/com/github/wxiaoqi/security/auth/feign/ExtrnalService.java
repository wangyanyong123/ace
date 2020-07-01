package com.github.wxiaoqi.security.auth.feign;

import com.github.wxiaoqi.security.auth.configuration.FeignConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:38 2018/12/25
 * @Modified By:
 */
@FeignClient(value = "${extrnal.service}", configuration = FeignConfiguration.class)
public interface ExtrnalService {

	@RequestMapping(value = "/eUser/getExtrnalUser", method = RequestMethod.POST)
	ObjectRestResponse getExtrnalUser(@RequestParam("appId") String appId);

	@RequestMapping(value = "/eUser/getExtrnalUserMenu", method = RequestMethod.POST)
	ObjectRestResponse getExtrnalUserMenu(@RequestParam("appId") String appId, @RequestParam("uri")String uri);
}
