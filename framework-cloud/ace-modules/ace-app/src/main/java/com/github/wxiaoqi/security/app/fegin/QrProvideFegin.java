package com.github.wxiaoqi.security.app.fegin;

import com.github.wxiaoqi.security.app.vo.visitor.out.Qr;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:42 2019/1/10
 * @Modified By:
 */
@FeignClient(value = "ace-external-service", configuration = FeignApplyConfiguration.class)
public interface QrProvideFegin {
	@GetMapping("/qr/deletePassQr")
	ObjectRestResponse deletePassQr(@RequestParam("userId")String userId);

	@GetMapping("/qr/generateFormalPassQr")
	ObjectRestResponse generateFormalPassQr(@RequestParam("userId")String userId);

	@GetMapping(value = "/pass/generateTempPassQr")
	public ObjectRestResponse<Qr> generateTempPassQr(@RequestParam("effTime")String effTime, @RequestParam("loseTime")String loseTime,
													 @RequestParam("enclosedId") String enclosedId, @RequestParam("tel") String tel,
													 @RequestParam("name") String name, @RequestParam("number") Integer number);
}
