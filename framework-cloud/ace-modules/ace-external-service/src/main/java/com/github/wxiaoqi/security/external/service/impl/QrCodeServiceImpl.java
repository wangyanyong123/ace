package com.github.wxiaoqi.security.external.service.impl;

import com.github.wxiaoqi.security.external.entity.BizQr;
import com.github.wxiaoqi.security.external.mapper.BizQrMapper;
import com.github.wxiaoqi.security.external.service.QrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:52 2019/1/3
 * @Modified By:
 */
@Service
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	private BizQrMapper qrMapper;
	@Override
	public BizQr getQrCodeInfo(String userId) {
		log.debug("getQrCodeInfo():userId:{}  " , userId);
		return qrMapper.getQrInfoByUserId(userId);
	}
}
