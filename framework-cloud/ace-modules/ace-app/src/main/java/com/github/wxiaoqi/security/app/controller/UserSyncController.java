package com.github.wxiaoqi.security.app.controller;


import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.DoOperateByTypeVo;
import com.github.wxiaoqi.security.app.biz.BaseAppClientUserBiz;
import com.github.wxiaoqi.security.app.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.app.biz.BizSubscribeWoBiz;
import com.github.wxiaoqi.security.app.vo.in.WoInfo;
import com.github.wxiaoqi.security.app.vo.order.ResponseEntity;
import com.github.wxiaoqi.security.auth.client.annotation.CheckExternalService;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Author: huangxl
 * @Description: 接收用户密码重置接口
 * @Date: Created in  2019/8/15
 */
@RestController
@RequestMapping("userSync")
@CheckExternalService
@Api(tags="接收用户密码重置接口")
@Slf4j
public class UserSyncController {

	@Autowired
	private BaseAppServerUserBiz baseAppServerUserBiz;

	@RequestMapping(value = "updateNewPassword", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "接收用户密码重置接口", notes = "接收用户密码重置接口",httpMethod = "POST")
	public ResponseEntity updateNewPassword(@RequestBody @ApiParam Map<String,String> userInfo){
		log.info("接收用户密码重置接口参数："+userInfo.toString());
		ResponseEntity responseEntity = new ResponseEntity();
		String userId = userInfo.get("userId");
		String password = userInfo.get("password");
		String mobilePhone = userInfo.get("mobilePhone");
		if(StringUtils.isAnyoneEmpty(userId,password,mobilePhone)){
			responseEntity.setCode("201");
			responseEntity.setDescribe("参数不能为空");
			return responseEntity;
		}
		ObjectRestResponse restResponse = baseAppServerUserBiz.updateNewPassword(mobilePhone,password,false);
		if(restResponse!=null){
			responseEntity.setCode(restResponse.getStatus()+"");
			responseEntity.setDescribe(restResponse.getMessage());
		}else{
			responseEntity.setCode("202");
			responseEntity.setDescribe("同步异常");
			return responseEntity;
		}
		return responseEntity;
	}

}
