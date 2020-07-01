package com.github.wxiaoqi.security.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BizAppUserFaceBiz;
import com.github.wxiaoqi.security.app.config.FaceConfig;
import com.github.wxiaoqi.security.app.entity.BizAppUserFace;
import com.github.wxiaoqi.security.app.entity.BizExternalUserPasslog;
import com.github.wxiaoqi.security.app.mapper.BizAppUserFaceMapper;
import com.github.wxiaoqi.security.app.mapper.BizCrmProjectMapper;
import com.github.wxiaoqi.security.app.mapper.BizCrmUnitMapper;
import com.github.wxiaoqi.security.app.mapper.BizExternalUserPasslogMapper;
import com.github.wxiaoqi.security.app.vo.face.*;
import com.github.wxiaoqi.security.app.vo.face.in.CheckFaceResponse;
import com.github.wxiaoqi.security.app.vo.face.in.CheckFaceVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.auth.client.jwt.MD5;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;

import com.github.wxiaoqi.security.common.util.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:21 2018/12/27
 * @Modified By:
 */
@RestController
@RequestMapping("face")
@CheckUserToken
@CheckClientToken
@Api(tags="人脸")
@Slf4j
public class FaceRecognitionController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private BizCrmUnitMapper bizCrmUnitMapper;
	@Autowired
	private BizExternalUserPasslogMapper bizExternalUserPasslogMapper;
	@Autowired
	private BizAppUserFaceMapper bizAppUserFaceMapper;
	@Autowired
	private BizAppUserFaceBiz bizAppUserFaceBiz;
	@Autowired
	private FaceConfig faceConfig;

	// 人脸接口关闭后的提示信息
	private ObjectRestResponse getResult(){
		ObjectRestResponse msg = new ObjectRestResponse();
		msg.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
		msg.setMessage("因疫情原因，人脸服务使用率较低，现对人脸服务进行优化升级，暂不支持人脸照片上传，已经上传过照片的用户可以正常使用服务。");
		return msg;
	}
	@PostMapping("uploadface")
	@ApiOperation(value = "上传人脸接口", notes = "上传人脸接口",httpMethod = "POST")
	public ObjectRestResponse uploadface(@RequestBody @ApiParam UploadVo uploadfaceVo){
		return getResult();
		// 人脸接口开启后放开代码
//		ObjectRestResponse msg = new ObjectRestResponse();
//		String url = faceConfig.getUploadUrl();
//		String appId = faceConfig.getAppId();
//		String userId = BaseContextHandler.getUserID();
//		List<Unit> unitInfo = bizCrmUnitMapper.getUnitByUser(userId,uploadfaceVo.getProjectId());
//		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		Calendar calendar = Calendar.getInstance();
//		String timestamp = df.format(calendar.getTime());
//		Map<String,Object> tempMap = new HashMap<String,Object>();
//		List<Map<String, Object>> unitList = new ArrayList<>();
//		for (Unit unit : unitInfo) {
//			Map<String, Object> unitMap = new HashMap<>();
//			unitMap.put("unitId", unit.getUnitId());
//			unitMap.put("elevatorAuthority", unit.getElevatorAuthority());
//			unitList.add(unitMap);
//		}
//		//用户类型(1-业主、2-访客、3-物业)
//		tempMap.put("userType","1");
//		tempMap.put("userName",userId);
//		tempMap.put("appId",appId);
//		tempMap.put("photoArray",uploadfaceVo.getPhotoArray());
//		tempMap.put("appUserId",userId);
//		tempMap.put("projectId",uploadfaceVo.getProjectId());
//		tempMap.put("unitList",unitList);
//		tempMap.put("timestamp",timestamp);
//		ObjectRestResponse response = bizAppUserFaceBiz.send(url, tempMap, timestamp);
//		CheckFaceResponse data = (CheckFaceResponse) response.getData();
//		if (data == null) {
//			msg.setStatus(101);
//			msg.setMessage("请求人脸厂商接口失败");
//			return msg;
//		}
//		if (data.getCode().equals("000")) {
//			String faceId = UUIDUtils.generateUuid();
//			BizAppUserFace bizAppUserFace = new BizAppUserFace();
//			bizAppUserFace.setId(faceId);
//			bizAppUserFace.setUserId(userId);
//			bizAppUserFace.setProjectId(uploadfaceVo.getProjectId());
//			if (unitInfo != null && unitInfo.size() > 0) {
//				String unitId = "";
//				StringBuilder sb = new StringBuilder();
//				for (Unit unit: unitInfo) {
//					sb.append(unit.getUnitId() + ",");
//				}
//				unitId = sb.substring(0,sb.length()-1);
//				bizAppUserFace.setUnitId(unitId);
//			}
//			if (uploadfaceVo.getPhotoArray() != null && uploadfaceVo.getPhotoArray().size() > 0) {
//				String photo = "";
//				StringBuilder sb = new StringBuilder();
//				for (String photoUrl : uploadfaceVo.getPhotoArray()) {
//					sb.append(photoUrl + ",");
//				}
//				photo = sb.substring(0, sb.length() - 1);
//				bizAppUserFace.setFacePhoto(photo);
//			}
//			bizAppUserFace.setCreateBy(BaseContextHandler.getUserID());
//			bizAppUserFace.setCreateTime(new Date());
//			msg = this.bizAppUserFaceBiz.syncCrmNewFace(uploadfaceVo);
//			if(!msg.success()){
//				log.info("人脸数据同步CRM失败：{}，message:{},userId:{},projectId:{},faceId:{}",msg.getStatus(),msg.getMessage(),userId,uploadfaceVo.getProjectId(),faceId);
//				msg.setStatus(102);
//				msg.setMessage("插入人脸数据失败");
//				return msg;
//			}
//			if (bizAppUserFaceMapper.insertSelective(bizAppUserFace) < 0) {
//				log.info("插入人脸数据失败,id为",faceId);
//				msg.setStatus(101);
//				msg.setMessage("插入人脸数据失败");
//				return msg;
//			}
//		}
//		return response;
	}

	@GetMapping("getface")
	@ApiOperation(value = "获取人脸信息", notes = "获取人脸信息", httpMethod = "GET")
	public ObjectRestResponse getFaceInfo() {
		 return bizAppUserFaceBiz.getFaceInfo(BaseContextHandler.getUserID());
	}

	@PostMapping("updateface")
	@ApiOperation(value = "更新人脸接口", notes = "更新人脸接口",httpMethod = "POST")
	public ObjectRestResponse updateface(@RequestBody @ApiParam ProjectInfoVo param) {
		return getResult();
		// 人脸接口开启后放开代码
//		ObjectRestResponse msg = new ObjectRestResponse();
//		if (bizAppUserFaceBiz.deleteFaceInfo(BaseContextHandler.getUserID()) >= 0) {
//			CheckFaceResponse data = (CheckFaceResponse) deleteface(param.getProjectId()).getData();
//			if (data == null) {
//				msg.setStatus(Integer.parseInt(data.getCode()));
//				msg.setMessage(data.getDescribe());
//				return msg;
//			}
//			if (!data.getCode().equals("000")) {
//				msg.setMessage(data.getDescribe());
//				msg.setStatus(Integer.valueOf(data.getCode()));
//				return msg;
//			}
//			msg.setStatus(200);
//			msg.setMessage("请重新上传人脸");
//		}else {
//			msg.setStatus(101);
//			msg.setMessage("更新人脸数据失败");
//		}
//		return msg;
	}

	@PostMapping("deleteFaceByUser")
	@ApiOperation(value = "删除用户时删除人脸", notes = "删除用户时删除人脸",httpMethod = "POST")
	public ObjectRestResponse deleteFaceByUser(@RequestBody @ApiParam UserFaceInfo param) {
		ObjectRestResponse msg = new ObjectRestResponse();
//		if (bizAppUserFaceBiz.deleteFaceInfo(BaseContextHandler.getUserID()) >= 0) {
//			CheckFaceResponse data = (CheckFaceResponse) deleteface(param.getProjectId()).getData();
//			if (data == null) {
//				msg.setStatus(Integer.parseInt(data.getCode()));
//				msg.setMessage(data.getDescribe());
//				return msg;
//			}
//			if (!data.getCode().equals("000")) {
//				msg.setMessage(data.getDescribe());
//				msg.setStatus(Integer.valueOf(data.getCode()));
//				return msg;
//			}
//			msg.setStatus(200);
//			msg.setMessage("请重新上传人脸");
//		}else {
//			msg.setStatus(101);
//			msg.setMessage("更新人脸数据失败");
//		}
		return msg;
	}

	private ObjectRestResponse deleteface(String projectId){
		String url = faceConfig.getDeleteUrl();
		String appId = faceConfig.getAppId();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String userId = BaseContextHandler.getUserID();
		List<Unit> unitInfo = bizCrmUnitMapper.getUnitByUser(userId,projectId);
		Calendar calendar = Calendar.getInstance();
		String timestamp = df.format(calendar.getTime());
		Map<String,Object> tempMap = new HashMap<String,Object>();
		List<Map<String, Object>> unitList = new ArrayList<>();
		for (Unit unit : unitInfo) {
			Map<String, Object> unitMap = new HashMap<>();
			unitMap.put("unitId", unit.getUnitId());
			unitMap.put("elevatorAuthority", unit.getElevatorAuthority());
			unitList.add(unitMap);
		}
		//用户类型(1-业主、2-访客、3-物业)
		tempMap.put("userType","1");
		tempMap.put("appId",appId);
		tempMap.put("appUserId",userId);
		tempMap.put("projectId",projectId);
		tempMap.put("unitList",unitList);
		tempMap.put("timestamp", timestamp);
		return bizAppUserFaceBiz.send(url,tempMap,timestamp);

	}

	@PostMapping(value = "getlog",produces = "application/json; utf-8")
	@ApiOperation(value = "获取用户通行记录接口", notes = "获取用户通行记录接口",httpMethod = "POST")
	public ObjectRestResponse getlog(@RequestBody @ApiParam GetLogVo logVo) throws ParseException {
		return getResult();
		// 人脸接口开启后放开代码
//		String url = faceConfig.getGetLogUrl();
//		String appId = faceConfig.getAppId();
//		String appSecret = faceConfig.getAppSecret();
//		String userId = BaseContextHandler.getUserID();
//		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//		Calendar calendar = Calendar.getInstance();
//		String timestamp = df.format(calendar.getTime());
//		Map<String,Object> tempMap = new HashMap<String,Object>();
//		tempMap.put("appId",appId);
//		tempMap.put("projectId",logVo.getProjectId());
//		tempMap.put("startDate",logVo.getStartDate());
//		tempMap.put("endDate",logVo.getEndDate());
//		tempMap.put("timestamp", timestamp);
//		ObjectRestResponse response = bizAppUserFaceBiz.send(url, tempMap, timestamp);
//		CheckFaceResponse passLogs = (CheckFaceResponse)response.getData();
//		if (passLogs != null) {
//			if (passLogs.getCode().equals("000")) {
//				for (CheckFaceVo logs : passLogs.getData()) {
//					BizExternalUserPasslog bizExternalUserPasslog = new BizExternalUserPasslog();
//					bizExternalUserPasslog.setId(UUIDUtils.generateUuid());
//					bizExternalUserPasslog.setCreateBy(BaseContextHandler.getUserID());
//					bizExternalUserPasslog.setCreateTime(new Date());
//					bizExternalUserPasslog.setUserId(logs.getAppUserId());
//					bizExternalUserPasslog.setPassAddr(logs.getPassAddr());
//					bizExternalUserPasslog.setPassStatus(logs.getPassStatus());
//					bizExternalUserPasslog.setPassTime(logs.getPassTime());
//					if (bizExternalUserPasslogMapper.insertSelective(bizExternalUserPasslog) < 0) {
//						log.info("插入通行日志失败，userID为{}",logs.getAppUserId());
//						response.setMessage("插入通行日志失败");
//						response.setStatus(101);
//					}
//				}
//			}
//		}
//		return response;
	}

//	private ObjectRestResponse send(String url,Map<String,Object> param,String timestamp) {
//		ObjectRestResponse response = new ObjectRestResponse();
//		String appId = faceConfig.getAppId();
//		String appSecret = faceConfig.getAppSecret();
//		appSecret = "appKey=" + appSecret;
//		String sign = Base64Utils.encodeToString(MD5.getSign(param,appSecret).getBytes());
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("appId",appId);
//		headers.add("sign",sign);
//		headers.add("timestamp",timestamp);
//		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		HttpEntity<Object> entity = new HttpEntity<Object>(param,headers);
//		log.info("请求人脸厂商地址：{}，参数{}", url,param.toString());
//		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//		if (200 == responseEntity.getStatusCodeValue()) {
//			String strbody = responseEntity.getBody();
//			CheckFaceResponse checkFaceResponse = JSONObject.parseObject(strbody, CheckFaceResponse.class);
//			if ("000".equals(checkFaceResponse.getCode())) {
//				log.info("请求人脸厂商成功！返回结果{}", checkFaceResponse.toString());
//				response.setData(checkFaceResponse);
//			} else {
//				log.error("请求人脸厂商返回结果提示失败！返回结果{}", checkFaceResponse.toString());
//				response.setStatus(Integer.valueOf(checkFaceResponse.getCode()));
//				response.setMessage(checkFaceResponse.getDescribe());
//				return response;
//			}
//		} else {
//			log.error("请求人脸厂商失败！返回结果{}", responseEntity.toString());
//			response.setStatus(500);
//			response.setMessage("请求人脸厂商失败！");
//			return response;
//		}
//		return response;
//	}


	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping(value = "syncHistoryFaceToCrm",produces = "application/json; utf-8")
	@ApiOperation(value = "同步历史人脸数据", notes = "同步历史人脸数据",httpMethod = "GET")
	public ObjectRestResponse syncHistoryFaceToCrm() throws ParseException {
		return bizAppUserFaceBiz.syncHistoryFaceToCrm();
	}
}
