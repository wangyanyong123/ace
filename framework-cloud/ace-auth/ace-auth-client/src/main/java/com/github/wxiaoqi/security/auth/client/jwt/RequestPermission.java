package com.github.wxiaoqi.security.auth.client.jwt;

import com.github.wxiaoqi.security.auth.client.dto.ApiHeadBean;
import com.github.wxiaoqi.security.auth.client.dto.ExternalUserVo;
import com.github.wxiaoqi.security.auth.client.feign.ServiceAuthFeign;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:14 2018/12/25
 * @Modified By:
 */
public class RequestPermission {
	private static Logger _log = LoggerFactory.getLogger(RequestPermission.class);

	private static ServiceAuthFeign serviceAuthFeign = BeanUtils.getBean(ServiceAuthFeign.class);

	/**
	 * 校验参数签名是否正确
	 * @param map
	 * @return
	 */
	public static ObjectRestResponse checkSign(Map<String,Object> map, ApiHeadBean apiHeadBean,String uri,String appKeyHead,String invalidTimeHead) throws Exception{
		ObjectRestResponse resultMsg = new ObjectRestResponse();
		Map<String,Object> tempMap = new HashMap<String,Object>();
		String appId = apiHeadBean.getAppId();
		String inputSign = apiHeadBean.getSign();
		for(Map.Entry<String,Object> entry:map.entrySet()){
			//过滤参数中的appKey、sign、invalidTime
			if("appId".equals(entry.getKey().toString()) || "sign".equals(entry.getKey().toString())
					|| "timestamp".equals(entry.getKey().toString())){

			}else {
				tempMap.put(entry.getKey(), entry.getValue());
			}
		}
		if(null == serviceAuthFeign){
			serviceAuthFeign = BeanUtils.getBean(ServiceAuthFeign.class);
		}
		ObjectRestResponse<ExternalUserVo> response = serviceAuthFeign.getExtrnalUser(appId);
		if(null!= response && 200 == (response.getStatus())) {
			ExternalUserVo userVo = response.getData();
			if(null != userVo){
				String appSecret = userVo.getAppSecret();
				if(StringUtils.isNotEmpty(appKeyHead) && StringUtils.isNotEmpty(invalidTimeHead)){
					tempMap.put("appKey", appKeyHead);
					tempMap.put("invalidTime",invalidTimeHead);
					appSecret = "appSecret=" + appSecret;
				}else{
					tempMap.put("timestamp", apiHeadBean.getTimestamp());
					tempMap.put("appId",apiHeadBean.getAppId());
					appSecret = "appKey=" + appSecret;
				}
				String sign = MD5.getSign(tempMap,appSecret);
				_log.info("md5加密值进行base64加密结果为："+Base64Utils.encodeToString(sign.getBytes()));
				//对传入sign进行base64解码
				String base64Sign = new String(Base64Utils.decode(inputSign.getBytes()));
				base64Sign = base64Sign.toUpperCase();
				_log.info("对传入sign进行base64解码结果为："+base64Sign);
				boolean result = base64Sign.equals(sign);
				if(!result) {
					resultMsg.setStatus(103);
					resultMsg.setMessage("Failure of parameter signature!");
				}else {
					ObjectRestResponse restResponse = serviceAuthFeign.getExtrnalUserMenu(appId,uri);
					if(null == restResponse || 200 != (restResponse.getStatus())){
						resultMsg.setStatus(105);
						resultMsg.setMessage("No authority!");
					}
				}
			}
		}else {
			resultMsg.setStatus(104);
			resultMsg.setMessage("Failed to obtain appid information!");
		}
		return resultMsg;
	}

}
