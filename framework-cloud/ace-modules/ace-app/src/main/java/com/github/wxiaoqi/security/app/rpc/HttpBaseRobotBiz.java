package com.github.wxiaoqi.security.app.rpc;


import com.github.wxiaoqi.security.app.config.VrobotConfig;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.SignUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/***
 * 与调度引擎系统接口同步
 */
@Service
@Slf4j
public class HttpBaseRobotBiz {

	@Autowired
	private VrobotConfig vrobotConfig;

	/**
	 * 基础请求类
	 * @param methodPath 方法路径
	 * @param object 请求参数
	 * @return
	 */
	public ObjectRestResponse requestPost(String methodPath, Object object) {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		HttpHeaders headers = new HttpHeaders();
		String url = vrobotConfig.getUrl()+ methodPath;
		String appKey = vrobotConfig.getAccessKey();
		String access_secret = vrobotConfig.getAccessSecret();
		String invalidTime = System.currentTimeMillis()+"";
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("appKey",appKey);
		headers.set("invalidTime",invalidTime);
		String signature = SignUtils.generateSign(object,appKey,invalidTime, access_secret);
		headers.set("sign",signature);
		HttpEntity<String> entity = null;
		JSONObject jsonObj = JSONObject.fromObject(object);
		if (jsonObj != null) {
			entity = new HttpEntity<String>(jsonObj.toString(),headers);
		}else {
			entity = new HttpEntity<String>(headers);
		}
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		//获取Apollo配置,请求超时时间
		String timeout = "60";
		if(StringUtils.isNotEmpty(timeout)){
			requestFactory.setConnectTimeout(Integer.valueOf(timeout)*1000);
			requestFactory.setReadTimeout(Integer.valueOf(timeout)*1000);
		}else{
			requestFactory.setConnectTimeout(5*1000);
			requestFactory.setReadTimeout(5*1000);
		}
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		log.info("请求调度引擎系统，接口URL是:"+url+",请求头参数是："+headers.toString()+",业务参数："+entity.toString());
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,String.class);
			if (responseEntity != null) {
				log.info("请求调度引擎系统，接口鉴权响应的结果:" + responseEntity.toString());
				String body = responseEntity.getBody();
                restResponse.setData(body);
			}else {
				restResponse.setStatus(201);
				restResponse.setMessage("请求调度引擎系统异常："+responseEntity.toString());
			}
		} catch (RestClientException e) {
			log.info("请求调度引擎连接超时");
			restResponse.setStatus(202);
			restResponse.setMessage("请求调度引擎连接超时");
		}
		return restResponse;
	}


}
