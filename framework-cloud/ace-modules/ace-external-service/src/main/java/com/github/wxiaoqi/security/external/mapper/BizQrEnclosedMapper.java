package com.github.wxiaoqi.security.external.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.external.entity.BizQrEnclosed;

/**
 * 
 * 
 * @author zxl
 * @Date 2019-01-07 15:19:39
 */
public interface BizQrEnclosedMapper extends CommonMapper<BizQrEnclosed> {

	String getEncloseIdByQrId(String id);
}
