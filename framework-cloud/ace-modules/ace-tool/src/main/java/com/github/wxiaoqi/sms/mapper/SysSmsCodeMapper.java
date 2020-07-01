package com.github.wxiaoqi.sms.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.sms.entity.SysSmsCode;
import org.apache.ibatis.annotations.Param;

/**
 * 短信验证码
 * 
 * @author zxl
 * @Date 2018-11-20 11:24:20
 */
public interface SysSmsCodeMapper extends CommonMapper<SysSmsCode> {

	SysSmsCode getLastCode(@Param("mobile") String mobile, @Param("volidCode") String volidCode);
}
