package com.github.wxiaoqi.security.external.controller;

import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.external.biz.BizQrBiz;
import com.github.wxiaoqi.security.external.config.QrConfiguration;
import com.github.wxiaoqi.security.external.entity.BizQr;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:25 2019/1/7
 * @Modified By:
 */
@RestController
@RequestMapping("pass")
//@CheckClientToken
@CheckUserToken
@Api(tags="通行码")
@Slf4j
public class AppPassController {
	@Autowired
	private BizQrBiz qrBiz;

	@Autowired
	private QrConfiguration qrConfiguration;

	// 1、生成正式码，
	@GetMapping(value = "generateFormalPassQr")
	@ApiIgnore
	public ObjectRestResponse generateFormalPassQr(){
		ObjectRestResponse response = new ObjectRestResponse();
		BizQr qr = qrBiz.generateFormalPassQr(null);
		if(null != qr){
			response.setData(qr);
		}else {
			response.setStatus(501);
			response.setMessage("当前用户还未认证房屋！");
		}
		return response;
	}

	@GetMapping(value = "getLastQrNum")
	@ApiOperation(value = "获取最新的流水号", notes = "获取最新的流水码",httpMethod = "GET")
	public ObjectRestResponse getLastQrNum(){
		ObjectRestResponse response = new ObjectRestResponse();
		String qrNum = qrBiz.getLastQrNum();
		if(!StringUtils.isEmpty(qrNum)){
			response.setData(qrNum);
		}else {
			response.setStatus(501);
			response.setMessage("当前用户还未认证房屋！");
		}
		return response;
	}

	@GetMapping(value = "qrRefreshTime")
	@ApiOperation(value = "获取正式码刷新时间单位s", notes = "获取正式码刷新时间单位s",httpMethod = "GET")
	public ObjectRestResponse qrRefreshTime(){
		log.info("获取到时间是:{}"+qrConfiguration.getRefreshTime());
		if(0 == qrConfiguration.getRefreshTime()){
			qrConfiguration.setRefreshTime(60);
		}
		return ObjectRestResponse.ok(qrConfiguration.getRefreshTime());
	}

	@GetMapping(value = "deletePassQr")
	@ApiIgnore
	public ObjectRestResponse deletePassQr(String userId){
		ObjectRestResponse response = new ObjectRestResponse();
		qrBiz.deletePassQr(userId);
		response.setMessage("删除成功！");
		return response;
	}

	// 2、生成临时码，
	@GetMapping(value = "generateTempPassQr")
	@ApiImplicitParams({
			@ApiImplicitParam(name="effTime",value="生效时间",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324"),
			@ApiImplicitParam(name="loseTime",value="失效时间",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324"),
			@ApiImplicitParam(name="enclosedId",value="围合id",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324"),
			@ApiImplicitParam(name="tel",value="邀请人手机号",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324"),
			@ApiImplicitParam(name="name",value="邀请人姓名",dataType="String",required = false ,paramType = "query",example="4sdadgsfdgs2324"),
			@ApiImplicitParam(name="number",value="次数",dataType="int",required = false ,paramType = "query",example="4sdadgsfdgs2324")
	})
	public ObjectRestResponse generateTempPassQr(String effTime,String loseTime,String enclosedId,String tel,String name,Integer number){
		ObjectRestResponse response = new ObjectRestResponse();
		if(null == number){
			number = 10;
		}
		if(StringUtils.isAnyoneEmpty(effTime,loseTime,enclosedId,tel)){
			response.setStatus(501);
			response.setMessage("参数不能为空！");
			return response;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		try {
			start = sdf.parse(effTime);
			end = sdf.parse(loseTime);
			if (! (end.getTime() > start.getTime())) {
				response.setMessage("时间错误");
				response.setStatus(502);
				return response;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		response = qrBiz.generateTempPassQr(start,end,enclosedId,tel,name,number.intValue());
		return response;
	}
	// 3、刷新正式码，
	@GetMapping(value = "refreshFormalPassQr")
	@ApiOperation(value = "刷新正式码", notes = "刷新正式码",httpMethod = "GET")
	@ApiImplicitParam(name="qrNum",value="流水号",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324")
	public ObjectRestResponse refreshFormalPassQr(String qrNum){
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(qrNum)){
			response.setStatus(502);
			response.setMessage("qrNum错误！");
			return response;
		}
		BizQr qr = qrBiz.refreshFormalPassQr(qrNum);
		if(null == qr){
			response.setStatus(501);
			response.setMessage("没有有效的二维码！");
			return response;
		}
		response.setMessage("刷新成功");
		response.setData(qr);
		return response;
	}
	// 4、判断当前用户是否有生效的正式码，
	@GetMapping(value = "isHasEffectiveFormalPassQr")
	@ApiIgnore
	public ObjectRestResponse isHasEffectiveFormalPassQr(){
		ObjectRestResponse response = new ObjectRestResponse();
		BizQr qr = qrBiz.isHasEffectiveFormalPassQr();
		response.setData(0);
		response.setMessage("不存在生效的二维码！");
		if(null != qr){
			response.setData(1);
			response.setMessage("存在生效的二维码！");
		}
		return response;
	}
}
