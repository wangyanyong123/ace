package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * app客户端用户表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
public interface BaseAppClientUserMapper extends CommonMapper<BaseAppClientUser> {

	UserVo getUserNameById(@Param("userId") String userId);

	int selectUserIntegralById(String userId);

	/**
	 *根据电话号码
	 * @param tel
	 * @param name
	 * @return
	 */
	UserVo getUserNameByTelOrName(@Param("tel") String tel,@Param("name")String name);
}
