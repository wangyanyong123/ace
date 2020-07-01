package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.SettlementBiz;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取支付配置模块
 *
 * @author huangxl
 * @Date 2018-12-25 16:00:01
 */
@RestController
@RequestMapping("settlement")
@CheckClientToken
@CheckUserToken
@Api(tags="获取支付配置模块")
public class SettlementController {

	@Autowired
	private SettlementBiz settlementBiz;


	/**
	 * APP通过实付ID获取支付宝结算信息
	 * 老版本获取支付信息，不在使用
	 * @param actualId 参数
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/getAliSettlementInfoByActualId" ,method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "通过实付ID获取支付宝结算信息", notes = "获取支付配置模块---通过实付ID获取支付宝结算信息",httpMethod = "GET")
	@ApiImplicitParam(name="actualId",value="实际支付ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse getAliSettlementInfoByActualId(String actualId) {
//		return settlementBiz.getAliSettlementInfoByActualId(actualId);
		ObjectRestResponse objectRestResponse = new ObjectRestResponse();
		objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
		objectRestResponse.setMessage("因服务安全升级需要，请您更新到最新的app版本。");
		return objectRestResponse;
	}


	/**
	 * APP通过实付ID获取微信结算信息
	 * @param actualId 参数
	 * @return
	 */
	@RequestMapping(value = "/getWechatSettlementInfoByActualId" ,method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "通过实付ID获取微信结算信息", notes = "获取支付配置模块---通过实付ID获取微信结算信息",httpMethod = "GET")
	@ApiImplicitParam(name="actualId",value="实际支付ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse getWechatSettlementInfoByActualId(String actualId) {
		return settlementBiz.getWechatSettlementInfoByActualId(actualId);
	}

}