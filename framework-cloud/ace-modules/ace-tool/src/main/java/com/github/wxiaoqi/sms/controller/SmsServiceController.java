package com.github.wxiaoqi.sms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.api.vo.order.in.CreateWoInVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.sms.entity.SysMobileInfo;
import com.github.wxiaoqi.sms.service.ShortMessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:21 2018/11/21
 * @Modified By:
 */
@RestController
@RequestMapping("smsService")
//@CheckClientToken
//@CheckUserToken
@Api(tags="消息工具接口")
public class SmsServiceController {

	@Autowired
	private ShortMessageService messageService;

	@GetMapping("/sendMsg")
	@ApiOperation(value = "发送消息", notes = "发送消息",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="mobilePhone",value="电话号码,如果是发送短信则为必填",dataType="MultipartFile",required = false ,paramType = "query",example="136****1548"),
			@ApiImplicitParam(name="num",value="验证码位数",dataType="String",required = false ,paramType = "query",example="4"),
			@ApiImplicitParam(name="lostSecond",value="验证码失效时间 单位：秒",dataType="String",required = false ,paramType = "query",example="60"),
			@ApiImplicitParam(name="bizType",value="业务类型：1、登录验证，会自动生成验证码；2、通知，默认为2",dataType="String",required = false ,paramType = "query",example="1"),
			@ApiImplicitParam(name="msgTheme",value="消息主体",dataType="String",required = true ,paramType = "query",example="CUSTOMER_SIDE_REG"),
			@ApiImplicitParam(name="email",value="收件人邮箱，如果是发送邮件则为必填",dataType="String",required = false ,paramType = "query",example="1@163.com"),
			@ApiImplicitParam(name="userId",value="用户id，如果是发送推送消息或者app内消息则为必填",dataType="String",required = false ,paramType = "query",example="wqewqeqeqdfsf"),
			@ApiImplicitParam(name="msgParam",value="消息中的参数",dataType="map转成json字符串",required = true ,paramType = "query",example="")
	})
	@IgnoreUserToken
	@IgnoreClientToken
	public ObjectRestResponse sendMsg(@RequestParam(value ="mobilePhone",required = false) String mobilePhone,
									   @RequestParam(value = "num" ,required = false) Integer num,
									   @RequestParam(value = "lostSecond" ,required = false) Integer lostSecond,
									   @RequestParam(value = "bizType",required = false ,defaultValue = "2") String bizType,
									   @RequestParam(value = "email",required = false) String email,
									   @RequestParam(value = "userId",required = false) String userId,
									   @RequestParam("msgTheme") String msgTheme,
									   @RequestParam(value = "msgParam",required = false) String msgParam){
		Map<String, String> paramMap = null;
		if(!StringUtils.isEmpty(msgParam)){
			paramMap = JSONObject.toJavaObject(JSON.parseObject(msgParam), Map.class);
		}
		return messageService.getCode(mobilePhone,num,lostSecond,bizType,email,userId,msgTheme,paramMap);
	}

	@GetMapping("/checkCode")
	@ApiOperation(value = "验证码认证", notes = "验证码认证",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="mobilePhone",value="电话号码",dataType="MultipartFile",required = true ,paramType = "query",example="136****1548"),
			@ApiImplicitParam(name="volidCode",value="验证码",dataType="String",required = true ,paramType = "query",example="4968")
	})
	public ObjectRestResponse checkCode(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("volidCode")String volidCode){
		return messageService.checkCode(mobilePhone,volidCode);
	}

	@GetMapping("/codeIsTrue")
	@ApiOperation(value = "验证码是否正确", notes = "验证码是否正确",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="mobilePhone",value="电话号码",dataType="MultipartFile",required = true ,paramType = "query",example="136****1548"),
			@ApiImplicitParam(name="volidCode",value="验证码",dataType="String",required = true ,paramType = "query",example="4968")
	})
	public ObjectRestResponse codeIsTrue(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("volidCode")String volidCode){
		return messageService.codeIsTrue(mobilePhone,volidCode);
	}


	/**
	 * 用户手机信息更新
	 * @return
	 */
	@GetMapping("/updateMobileInfo")
	@ApiOperation(value = "用户手机信息更新", notes = "用户手机信息更新",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="cid",value="cid",dataType="MultipartFile",required = true ,paramType = "query",example="136****1548"),
			@ApiImplicitParam(name="userId",value="userId",dataType="String",required = true ,paramType = "query",example="4968")
	})
	public ObjectRestResponse updateMobileInfo(@RequestParam("cid") String cid, @RequestParam("userId") String userId,
											   @RequestParam("clientType") String clientType, @RequestParam("os") String os,
											   @RequestParam("osVersion") String osVersion, @RequestParam("version") String version,
											   @RequestParam("macId") String macId) {
		ObjectRestResponse result = new ObjectRestResponse();
		SysMobileInfo mobileInfo = new SysMobileInfo();
		mobileInfo.setCid(cid);
		mobileInfo.setUserId(userId);
		mobileInfo.setClientType(clientType);
		mobileInfo.setOs(os);
		mobileInfo.setOsVersion(osVersion);
		mobileInfo.setVersion(version);
		mobileInfo.setMacId(macId);

		if(StringUtils.isEmpty(mobileInfo.getCid())){//没取到CID不更新数据
//			result.setStatus(101);
//			result.setMessage("没取到CID不更新数据");
			return result;
		}
		return messageService.updateMobileInfo(mobileInfo);
	}
}
