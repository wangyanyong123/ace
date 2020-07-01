package com.github.wxiaoqi.security.external.biz;

import com.github.wxiaoqi.security.auth.client.dto.ExternalUserVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.external.entity.BizExternalUser;
import com.github.wxiaoqi.security.external.mapper.BizExternalUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 对外提供接口用户信息
 *
 * @author zxl
 * @Date 2018-12-25 18:23:09
 */
@Service
public class BizExternalUserBiz extends BusinessBiz<BizExternalUserMapper,BizExternalUser> {

	@Autowired
	private BizExternalUserMapper externalUserMapper;

	public ObjectRestResponse<ExternalUserVo> getExtrnalUser(String appId) {
		ObjectRestResponse<ExternalUserVo> restResponse = new ObjectRestResponse<>();
		BizExternalUser param = new BizExternalUser();
		param.setAppId(appId);
		param.setStatus("1");
		BizExternalUser externalUser = this.selectOne(param);
		if(externalUser!=null){
			ExternalUserVo externalUserVo = new ExternalUserVo();
			BeanUtils.copyProperties(externalUser,externalUserVo);
			restResponse.setData(externalUserVo);
		}else{
			restResponse.setStatus(101);
			restResponse.setMessage("未找到应用信息");
		}
		return restResponse;
	}

	public ObjectRestResponse getExtrnalUserMenu(String appId, String uri) {
		ObjectRestResponse response = new ObjectRestResponse();
		int num = externalUserMapper.getExtrnalUserMenu(appId,uri);
		if(num < 1){
			response.setStatus(501);
			response.setMessage("没权限！");
			return response;
		}
		return response;
	}
}