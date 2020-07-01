package com.github.wxiaoqi.security.external.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.external.entity.BaseAppClientUser;
import org.apache.ibatis.annotations.Param;

/**
 * app客户端用户表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
public interface BaseAppClientUserMapper extends CommonMapper<BaseAppClientUser> {

	/**
	 * 查询用户id
	 * @param tel
	 * @return
	 */
	String selectUserIdByTel(String tel);

	/**
	 * 插入消息模板内容
	 * @param content
	 * @param userId
	 * @param code
	 * @return
	 */
	int insertContentMsgByCode(@Param("content") String content, @Param("userId") String userId, @Param("code") String code);
}
