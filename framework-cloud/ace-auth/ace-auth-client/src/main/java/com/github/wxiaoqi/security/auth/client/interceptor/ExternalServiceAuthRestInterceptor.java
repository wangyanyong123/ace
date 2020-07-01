package com.github.wxiaoqi.security.auth.client.interceptor;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.auth.client.annotation.CheckExternalService;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreCheckExternalService;
import com.github.wxiaoqi.security.auth.client.dto.ApiHeadBean;
import com.github.wxiaoqi.security.auth.client.jwt.RequestPermission;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:10 2018/12/21
 * @Modified By:
 */
public class ExternalServiceAuthRestInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(ExternalServiceAuthRestInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String method = request.getMethod();
		ServletOutputStream out = response.getOutputStream();
		if (HttpMethod.OPTIONS.matches(method)) {
			return super.preHandle(request, response, handler);
		}
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 配置该注解，说明不进行服务拦截
		CheckExternalService annotation = handlerMethod.getBeanType().getAnnotation(CheckExternalService.class);
		IgnoreCheckExternalService ignoreCheckExternalService = handlerMethod.getMethodAnnotation(IgnoreCheckExternalService.class);
		if (annotation == null) {
			annotation = handlerMethod.getMethodAnnotation(CheckExternalService.class);
		}
		if (annotation == null || ignoreCheckExternalService != null) {
			return super.preHandle(request, response, handler);
		} else {
			String uri = request.getRequestURI();
			String charset = request.getCharacterEncoding();
			String contentType = request.getContentType();

			ApiHeadBean apiHeadBean = new ApiHeadBean();
			apiHeadBean.setSign(request.getHeader("sign"));
			apiHeadBean.setAppId(request.getHeader("appId"));
			String appKeyHead = "";
			String invalidTimeHead = "";
			if(StringUtils.isEmpty(apiHeadBean.getAppId())){
				appKeyHead = request.getHeader("appKey");
				apiHeadBean.setAppId(appKeyHead);
			}
			apiHeadBean.setTimestamp(request.getHeader("timestamp"));
			if(StringUtils.isEmpty(apiHeadBean.getTimestamp())){
				invalidTimeHead = request.getHeader("invalidTime");
				String invalidTimeStr = DateUtils.timeStampToTime(invalidTimeHead,"yyyyMMddHHmmss");
				apiHeadBean.setTimestamp(invalidTimeStr);
			}
			if(StringUtils.isAnyoneEmpty(apiHeadBean.getAppId(),apiHeadBean.getSign(),apiHeadBean.getTimestamp())) {
				logger.error("头参数必填项为空，"+apiHeadBean.toString());
				response.reset();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				out.print(JSON.toJSONString(new BaseResponse(101, "Head parameter must be blank!")));
				return false;
			}
			Date invalidTime = DateUtils.stringToDate(apiHeadBean.getTimestamp(), "yyyyMMddHHmmss");
			int secondBetween = DateUtils.secondBetween(invalidTime, new Date());
			if(secondBetween > 5*60 || secondBetween < -5*60) {
				logger.error("请求接口链接已过期");
				response.reset();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				out.print(JSON.toJSONString(new BaseResponse(102, "Request interface link expired!")));
				return false;
			}

			Map<String,Object> map = new HashMap<String,Object>();
			String submitMehtod = request.getMethod();
			if (submitMehtod.equals("GET")) {
				Map<String,String[]> a = request.getParameterMap();
				Set<String> keys = a.keySet();
				for(String key:keys){
					map.put(key, a.get(key)[0]);
				}
			}else {
				RequestWrapper requestWrapper = new RequestWrapper(request);
				String requestPostStr = requestWrapper.getBody();
				if(StringUtils.isNotEmpty(requestPostStr)) {
					map = JacksonJsonUtil.jsonToBean(requestPostStr, Map.class);
				}
			}
			logger.info("接口请求参数为，"+map.toString());
			if(StringUtils.isEmpty(uri)){
				logger.error("uri为空");
				response.reset();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				out.print(JSON.toJSONString(new BaseResponse(106, "uri is Empty")));
				return false;
			}
			ObjectRestResponse msg = RequestPermission.checkSign(map,apiHeadBean,uri,appKeyHead,invalidTimeHead);
			if(200 == msg.getStatus()){
				return super.preHandle(request, response, handler);
			}
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			out.print(JSON.toJSONString(new BaseResponse(msg.getStatus(), msg.getMessage())));
			out.flush();
			out.close();
			return false;
		}
	}
	private static String getRequestPostStr(HttpServletRequest request)
			throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}
	private static byte[] getRequestPostBytes(HttpServletRequest request)
			throws IOException {
		int contentLength = request.getContentLength();
		if(contentLength<0){
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i,
					contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}
}

