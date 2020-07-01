package com.github.wxiaoqi.sms.biz;

import com.github.wxiaoqi.sms.entity.SysSmsCode;
import com.github.wxiaoqi.sms.mapper.SysSmsCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 短信验证码
 *
 * @author zxl
 * @Date 2018-11-20 11:24:20
 */
@Service
public class SysSmsCodeBiz extends BusinessBiz<SysSmsCodeMapper,SysSmsCode> {

	@Autowired
	private SysSmsCodeMapper smsCodeMapper;
	public SysSmsCode getLastCode(String mobile, String volidCode) {
		return smsCodeMapper.getLastCode(mobile, volidCode);
	}
}