package com.github.wxiaoqi.security.external.biz;

import com.github.wxiaoqi.security.external.entity.BizPass;
import com.github.wxiaoqi.security.external.mapper.BizPassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.Map;

/**
 * 
 *
 * @author zxl
 * @Date 2019-01-02 18:36:27
 */
@Service
public class BizPassBiz extends BusinessBiz<BizPassMapper,BizPass> {
	@Autowired
	private BizPassMapper passMapper;

	public int updatePassLog(Map<String, Object> passData) {
		return passMapper.updatePassLog(passData);
	}

}