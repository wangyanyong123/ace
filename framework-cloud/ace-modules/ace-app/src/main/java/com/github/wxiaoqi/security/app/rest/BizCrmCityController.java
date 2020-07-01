package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizCrmCityBiz;
import com.github.wxiaoqi.security.app.vo.city.out.CityInfoVo;
import com.github.wxiaoqi.security.app.vo.city.out.CityProjectInfoVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 城市表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@RestController
@RequestMapping("city")
//@CheckClientToken
@CheckUserToken
@Api(tags = "城市信息管理接口")
public class BizCrmCityController {
	@Autowired
	private BizCrmCityBiz crmCityBiz;

	@GetMapping(value = "/getCityList")
	@ApiOperation(value = "获取城市列表", notes = "获取城市列表",httpMethod = "GET")
	@ApiImplicitParam(name="cityName",value="城市名",dataType="String",required = false ,paramType = "query",example="深圳市")
	public ObjectRestResponse<List<CityInfoVo>> getCityList(String cityName){
		return crmCityBiz.getCityList(cityName);
	}

	@GetMapping(value = "/getCityProjectList")
	@ApiOperation(value = "获取城市-社区列表", notes = "获取城市-社区列表",httpMethod = "GET")
	@ApiImplicitParam(name="cityId",value="城市id",dataType="String",required = false ,paramType = "query",example="深圳市")
	public ObjectRestResponse<List<CityProjectInfoVo>> getCityProjectList(String cityId){
		return crmCityBiz.getCityProjectList(cityId);
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping(value = "/getCityListNoLogin")
	@ApiOperation(value = "获取城市列表", notes = "获取城市列表",httpMethod = "GET")
	@ApiImplicitParam(name="cityName",value="城市名",dataType="String",required = false ,paramType = "query",example="深圳市")
	public ObjectRestResponse<List<CityInfoVo>> getCityListNoLogin(String cityName){
		return crmCityBiz.getCityList(cityName);
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping(value = "/getCityProjectListNoLogin")
	@ApiOperation(value = "获取城市-社区列表", notes = "获取城市-社区列表",httpMethod = "GET")
	@ApiImplicitParam(name="cityId",value="城市id",dataType="String",required = false ,paramType = "query",example="深圳市")
	public ObjectRestResponse<List<CityProjectInfoVo>> getCityProjectListNoLogin(String cityId){
		return crmCityBiz.getCityProjectList(cityId);
	}
}