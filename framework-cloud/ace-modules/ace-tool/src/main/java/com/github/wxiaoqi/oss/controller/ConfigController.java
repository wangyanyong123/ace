package com.github.wxiaoqi.oss.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.github.wxiaoqi.config.CloudStorageConfig;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:23 2018/11/30
 * @Modified By:
 */
@RestController
@RequestMapping("config")
@Api(tags="配置管理")
@Slf4j
public class ConfigController {

	@Autowired
	private CloudStorageConfig storageConfig;

	@GetMapping("getOssConfig")
//	@CheckUserToken
	@ApiOperation(value = "获取上传oss路径", notes = "获取上传oss路径", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="isPub",value="是否公有(1-公有，2-私有)",dataType="String",required = true ,paramType = "query",example="1")
	})
	public ObjectRestResponse getOssConfig(@RequestParam("isPub")String isPub) {
		HashMap<String, String> ossData = new HashMap<>();
		String url = storageConfig.getAliyunPrefix();
		String logUrl = url + "/VISITLOG/01_WAIT_HANDLE";
		String bucket = "";
		if("1".equals(isPub)){
			url += "/temp" + "/" +  new SimpleDateFormat("yyyyMMdd").format(new Date());
			bucket = storageConfig.getAliyunBucketName();
		} else if("2".equals(isPub)) {
			url += "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
			bucket = storageConfig.getAliyunBucketName();
		}
		ossData.put("key", url);
		ossData.put("logUrl", logUrl);
		ossData.put("bucket", bucket);
		ossData.put("domain",storageConfig.getAliyunDomain());
		ossData.put("aliyunChatImgPath",storageConfig.getAliyunChatImgPath());
		ossData.put("aliyunChatVoicePath",storageConfig.getAliyunChatVoicePath());
		ossData.put("aliyunChatVideoPath",storageConfig.getAliyunChatVideoPath());
		ossData.put("aliyunChatThumbnailRatio",storageConfig.getAliyunChatThumbnailRatio());
		return ObjectRestResponse.ok(ossData);
	}


	@GetMapping("getSTSToken")
	@ApiOperation(value = "获取阿里云StsToken", notes = "获取阿里云StsToken", httpMethod = "GET")
//	@CheckUserToken
	public ObjectRestResponse getSTSToken() {
		ObjectRestResponse objectRestResponse = new ObjectRestResponse();
		// 只有 RAM用户（子账号）才能调用 AssumeRole 接口
		// 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
		// 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
		String accessKeyId = storageConfig.getAliyunSonAccountAccesskeyId();
		String accessKeySecret = storageConfig.getAliyunSonAccountaAccessKeySecret();
		// RoleArn 需要在 RAM 控制台上获取
		String roleArn = storageConfig.getAliyunStsRoleArn();

		// RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
		// 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '.' '@' 字母和数字等字符
		// 具体规则请参考API文档中的格式要求
		String roleSessionName = "alice-001";
		String policy=null;

		// 此处必须为 HTTPS
		ProtocolType protocolType = ProtocolType.HTTPS;

		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(storageConfig.getAliyunRegion(), accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(storageConfig.getAliyunStsApiVersion());
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);

			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);

			// 发起请求，并得到response
			AssumeRoleResponse response = client.getAcsResponse(request);
			if(response!=null && response.getCredentials()!=null && StringUtils.isNotEmpty(response.getCredentials().getSecurityToken())){
				objectRestResponse.setData(response.getCredentials());
				log.info("Expiration: " + response.getCredentials().getExpiration());
				log.info("Access Key Id: " + response.getCredentials().getAccessKeyId());
				log.info("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
				log.info("Security Token: " + response.getCredentials().getSecurityToken());
			}else{
				objectRestResponse.setStatus(501);
				objectRestResponse.setMessage("获取阿里云StsToken失败");
			}
		} catch (ClientException e) {
			log.error("Failed to get a token,Error code: " + e.getErrCode()+",Error message: " + e.getErrMsg());
			objectRestResponse.setStatus(502);
			objectRestResponse.setMessage("获取阿里云StsToken失败:"+e.getErrMsg());
		}
		return objectRestResponse;
	}
}
