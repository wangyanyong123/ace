package com.github.wxiaoqi.security.external.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.external.entity.BizExternalUser;
import org.apache.ibatis.annotations.Param;

/**
 * 对外提供接口用户信息
 * 
 * @author zxl
 * @Date 2018-12-25 18:23:09
 */
public interface BizExternalUserMapper extends CommonMapper<BizExternalUser> {

	int getExtrnalUserMenu(@Param("appId") String appId, @Param("uri")String uri);
}
