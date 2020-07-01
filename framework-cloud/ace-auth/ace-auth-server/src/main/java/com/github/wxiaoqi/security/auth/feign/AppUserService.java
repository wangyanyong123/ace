package com.github.wxiaoqi.security.auth.feign;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:48 2019/3/5
 * @Modified By:
 */

import com.github.wxiaoqi.security.auth.configuration.FeignConfiguration;
import com.github.wxiaoqi.security.auth.module.oauth.vo.HouseInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "ace-app", configuration = FeignConfiguration.class)
public interface  AppUserService {

	@RequestMapping(value = "/serverUser/getUserInfoByUsername", method = RequestMethod.POST)
	public ObjectRestResponse<Map<String, String>> getUserInfoByUsername(@RequestParam("username") String username);

	@RequestMapping(value = "/userHouse/getCurrentHouse", method = RequestMethod.GET)
	public ObjectRestResponse<HouseInfoVo> getCurrentHouse();

}
