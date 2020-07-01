package com.github.wxiaoqi.security.external.service;

import com.github.wxiaoqi.security.external.entity.BizQr;
import com.github.wxiaoqi.security.external.vo.PassportInfo;

import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:18 2019/1/2
 * @Modified By:
 */
public interface PassEventService {

	/**
	 *
	 * @param passPort
	 */
	PassportInfo handle(String passPort , String machineCode);

	/**
	 * 异步记录通行成功记录
	 */
	void passLog(final String userId , final String qrType ,
				 final String qrVal , final String facilitiesCode,final String passType,final String passAddr);

	/**
	 * 异步记录通行失败记录
	 */
	void passFalureLog(String userId, String qrType, String qrVal, String facilitiesCode,final String passType,final String passStatus, String passDesc,final String passAddr);

	/**
	 *
	 * @author yuwz 2016年8月5日
	 * @param facilitiesCode
	 * @return
	 */
	String getFacilitiesTypeByCode(String facilitiesCode);


}
