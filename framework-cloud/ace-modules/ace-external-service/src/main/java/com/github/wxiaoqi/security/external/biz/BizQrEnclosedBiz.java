package com.github.wxiaoqi.security.external.biz;

import com.github.wxiaoqi.security.external.entity.BizQrEnclosed;
import com.github.wxiaoqi.security.external.mapper.BizQrEnclosedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 
 *
 * @author zxl
 * @Date 2019-01-07 15:19:39
 */
@Service
public class BizQrEnclosedBiz extends BusinessBiz<BizQrEnclosedMapper,BizQrEnclosed> {
	@Autowired
	private BizQrEnclosedMapper qrEnclosedMapper;

	public String getEncloseIdByQrId(String id) {
		return qrEnclosedMapper.getEncloseIdByQrId(id);
	}
}