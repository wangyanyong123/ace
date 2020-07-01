package com.github.wxiaoqi.security.external.service.impl;

import com.github.wxiaoqi.security.external.biz.BizQrBiz;
import com.github.wxiaoqi.security.external.service.EntranceGuardBusiness;
import com.github.wxiaoqi.security.external.service.PassEventService;
import com.github.wxiaoqi.security.external.vo.PassportInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:43 2019/1/2
 * @Modified By:
 */
@Service
public class EntranceGuardBusinessImpl implements EntranceGuardBusiness {

	@Autowired
	private PassEventService passEventService;

	@Autowired
	private BizQrBiz qrBiz;

	@Override
	public PassportInfo doPassEvent(String passportCode , String machineCode) {
		return passEventService.handle(passportCode , machineCode);
	}

	@Override
	public String doOpenDoorNew(String machineCode, String passportCode) {
		return qrBiz.openDoorNew(machineCode, passportCode);
	}



	@Override
	public PassportInfo getLastPassportByToken(String token,String userId) {
		return qrBiz.getLastPassportByToken(token,userId);
	}

	@Override
	public void updatePublicQr(String userId) {
		qrBiz.updatePublicQr(userId);
	}
	/**
	 *
	 * @author yuwz 2016年8月5日
	 * @param facilitiesCode
	 * @return
	 */
	@Override
	public String getFacilitiesTypeByCode(String facilitiesCode){
		return passEventService.getFacilitiesTypeByCode(facilitiesCode);
	}
}

