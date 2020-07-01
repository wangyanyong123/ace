package com.github.wxiaoqi.security.external.provide;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.external.biz.BizQrBiz;
import com.github.wxiaoqi.security.external.entity.BizQr;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:22 2019/1/10
 * @Modified By:
 */
@RestController
@RequestMapping("qr")
@CheckClientToken
@CheckUserToken
@Api(tags="通行码")
@ApiIgnore
public class QrProvideController {
	@Autowired
	private BizQrBiz qrBiz;

	@GetMapping(value = "generateFormalPassQr")
	public ObjectRestResponse generateFormalPassQr(@RequestParam(value = "userId",required = false) String userId){
		ObjectRestResponse response = new ObjectRestResponse();
		BizQr qr = qrBiz.generateFormalPassQr(userId);
		if(null != qr){
			response.setData(qr);
		}else {
			response.setStatus(501);
			response.setMessage("当前用户还未认证房屋！");
		}
		return response;
	}
	@GetMapping(value = "deletePassQr")
	public ObjectRestResponse deletePassQr(@RequestParam(value = "userId") String userId){
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(userId)){
			response.setStatus(501);
			response.setMessage("userId不能为空！");
			return response;
		}
		qrBiz.deletePassQr(userId);
		response.setMessage("删除成功！");
		return response;
	}
}
