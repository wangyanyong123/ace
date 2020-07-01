package com.github.wxiaoqi.log.rest;

import com.github.wxiaoqi.log.service.BizLogService;
import com.github.wxiaoqi.log.vo.UserInfoVo;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author zxl
 * @Date 2019-04-08 15:50:07
 */
@RestController
@RequestMapping("log")
@IgnoreClientToken
@IgnoreUserToken
@Api(tags="日志")
public class BizLogController {

	@Autowired
	private BizLogService logService;

	@PostMapping(value = "/savelog")
	@ApiOperation(value = "保存日志", notes = "保存日志", httpMethod = "POST")
	public ObjectRestResponse savelog(@RequestBody @ApiParam LogInfoVo logInfoVo) {
		try {
			logService.savelog(logInfoVo);
		}catch (Exception e){
			e.printStackTrace();
			return ObjectRestResponse.ok();
		}
		return ObjectRestResponse.ok();
	}

	@GetMapping(value = "/getUserInfoById")
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value="用户id",dataType="String",required = true ,paramType = "query",example="1")
	})
	public ObjectRestResponse<UserInfoVo> getUserInfoById(String userId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(userId)){
			response.setStatus(500);
			response.setMessage("userId不能为空！");
			return response;
		}
		return logService.getUserInfoById(userId);
	}

	@GetMapping(value = "/getLoginLogs")
	@ApiOperation(value = "获取登录日志列表", notes = "获取登录日志列表", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="logName",value="日志名称",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="userName",value="用户名",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="account",value="账号",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="userType",value="类型 1、web，2、c，3、s",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="ip",value="ip",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="startTime",value="起止时间",dataType="String",paramType = "query",example="yyyy-MM-dd HH:mm:ss"),
			@ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="yyyy-MM-dd HH:mm:ss"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<LogInfoVo> getLoginLogs(String logName, String userName, String account, String userType, String ip, String startTime,
															 String endTime, Integer page, Integer limit) {
		if(StringUtils.isEmpty(userType)){
			userType = null;
		}
		return logService.getLoginLogs(logName,userName,account,userType,ip,startTime,endTime,page,limit);
	}

	@GetMapping(value = "/getBusLogs")
	@ApiOperation(value = "获取业务日志列表", notes = "获取业务日志列表", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="busName",value="业务名称",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="userName",value="用户名",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="account",value="账号",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="opt",value="操作",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="uri",value="资源路径",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="ip",value="ip",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="startTime",value="起止时间",dataType="String",paramType = "query",example="yyyy-MM-dd HH:mm:ss"),
			@ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="yyyy-MM-dd HH:mm:ss"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<LogInfoVo> getBusLogs(String busName, String userName, String account, String opt, String uri, String ip, String startTime,
													   String endTime, Integer page, Integer limit) {
		return logService.getBusLogs(busName,userName,account,opt,uri,ip,startTime,endTime,page,limit);
	}

	@GetMapping(value = "/getBusLogDetial")
	@ApiOperation(value = "获取业务日志详情", notes = "获取业务日志详情", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="id",value="id",dataType="String",required = true,paramType = "query",example="4")
	})
	public ObjectRestResponse getBusLogDetial(String id) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(id)){
			response.setStatus(500);
			response.setMessage("id不能为空！");
			return response;
		}
		return logService.getBusLogDetial(id);
	}

	@GetMapping(value = "/getSysLogs")
	@ApiOperation(value = "获取系统日志列表", notes = "获取系统日志列表", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="logName",value="日志名称",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="userName",value="用户名",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="account",value="账号",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="type",value="类型 1、内部，2、第三方",dataType="String",paramType = "query",example="4"),
			@ApiImplicitParam(name="ip",value="ip",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="startTime",value="起止时间",dataType="String",paramType = "query",example="yyyy-MM-dd HH:mm:ss"),
			@ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="yyyy-MM-dd HH:mm:ss"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<LogInfoVo> getSysLogs(String logName, String userName, String account, String type, String ip, String startTime,
													 String endTime, Integer page, Integer limit) {
		return logService.getSysLogs(logName,userName,account,type,ip,startTime,endTime,page,limit);
	}

	@GetMapping(value = "/getSysLogDetail")
	@ApiOperation(value = "获取系统日志详情", notes = "获取系统日志详情", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="id",value="id",dataType="String",required = true,paramType = "query",example="4")
	})
	public ObjectRestResponse getSysLogDetail(String id) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(id)){
			response.setStatus(500);
			response.setMessage("id不能为空！");
			return response;
		}
		return logService.getSysLogDetail(id);
	}

}