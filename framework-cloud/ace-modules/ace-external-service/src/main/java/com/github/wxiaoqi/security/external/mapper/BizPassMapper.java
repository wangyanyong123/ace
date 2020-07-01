package com.github.wxiaoqi.security.external.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.external.entity.BizPass;

import java.util.Map;

/**
 * 
 * 
 * @author zxl
 * @Date 2019-01-02 18:36:27
 */
public interface BizPassMapper extends CommonMapper<BizPass> {

	int updatePassLog(Map<String, Object> passData);
}
